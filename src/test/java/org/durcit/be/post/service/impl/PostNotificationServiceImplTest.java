package org.durcit.be.post.service.impl;

import org.durcit.be.follow.dto.MemberFollowResponse;
import org.durcit.be.post.domain.Post;
import org.durcit.be.post.dto.NotificationMessage;
import org.durcit.be.post.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PostNotificationServiceImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PostNotificationServiceImpl postNotificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("팔로워들에게 메시지를 전송 -> 성공")
    void notifyFollowers_shouldSendMessagesToRabbitMQ() {
        // given
        MemberFollowResponse follower1 = MemberFollowResponse.builder()
                .memberId(1L)
                .username("follower1")
                .build();
        MemberFollowResponse follower2 = MemberFollowResponse.builder()
                .memberId(2L)
                .username("follower2")
                .build();
        List<MemberFollowResponse> followers = List.of(follower1, follower2);

        NotificationMessage expectedMessage1 = NotificationMessage.builder()
                .followerId(1L)
                .postId(10L)
                .message("author1님이 새 글을 작성하였습니다!")
                .build();

        NotificationMessage expectedMessage2 = NotificationMessage.builder()
                .followerId(2L)
                .postId(10L)
                .message("author1님이 새 글을 작성하였습니다!")
                .build();

        // when
        postNotificationService.notifyFollowers(
                PostResponse.builder()
                        .id(10L)
                        .author("author1")
                        .title("title1")
                        .build(),
                followers
        );

        // then
        verify(rabbitTemplate, times(2)).convertAndSend(eq("postExchange"), eq("post.notify"), any(NotificationMessage.class));

        // 메시지 캡처
        ArgumentCaptor<NotificationMessage> messageCaptor = ArgumentCaptor.forClass(NotificationMessage.class);
        verify(rabbitTemplate, times(2)).convertAndSend(eq("postExchange"), eq("post.notify"), messageCaptor.capture());

        List<NotificationMessage> sentMessages = messageCaptor.getAllValues();
        assertThat(sentMessages).containsExactlyInAnyOrder(expectedMessage1, expectedMessage2);
    }
}