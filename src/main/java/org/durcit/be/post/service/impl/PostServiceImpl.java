package org.durcit.be.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.durcit.be.post.domain.Post;
import org.durcit.be.post.dto.PostRegisterRequest;
import org.durcit.be.post.dto.PostResponse;
import org.durcit.be.post.dto.PostUpdateRequest;
import org.durcit.be.post.repository.PostRepository;
import org.durcit.be.post.service.PostService;
import org.durcit.be.security.domian.Member;
import org.durcit.be.security.service.MemberService;
import org.durcit.be.security.util.SecurityUtil;
import org.durcit.be.system.aop.annotations.PostRequireAuthorization;
import org.durcit.be.system.aop.annotations.RequireCurrentMemberId;
import org.durcit.be.system.exception.post.PostNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.durcit.be.system.exception.ExceptionMessage.POST_NOT_FOUND_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .filter(post -> !post.isDeleted())
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        return postRepository.findById(postId)
                .filter(post -> !post.isDeleted())
                .map(PostResponse::fromEntity)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND_ERROR));
    }

    public Post getById(Long postId) {
        return postRepository.findById(postId)
                .filter(post -> !post.isDeleted())
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND_ERROR));
    }

    // aop
    // post 수정 삭제 어드민은 다가능, 나머지는 자기가 작성한 게시물 이어야 가능

    @Transactional
    @RequireCurrentMemberId
    public PostResponse createPost(PostRegisterRequest postRegisterRequest) {
        Member member = memberService.getById(SecurityUtil.getCurrentMemberId());
        Post post = PostRegisterRequest.toEntity(postRegisterRequest);
        post.setMember(member);
        return PostResponse.fromEntity(postRepository.save(post));
    }

    @Transactional
    @RequireCurrentMemberId
    @PostRequireAuthorization
    public void updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = getById(postId);
        post.updatePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        postRepository.save(post);
    }

    @Transactional
    @RequireCurrentMemberId
    @PostRequireAuthorization
    public void deletePost(Long postId) {
        Post post = getById(postId);
        post.setDeleted(true);
        postRepository.save(post);
    }


}
