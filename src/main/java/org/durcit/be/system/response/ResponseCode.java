package org.durcit.be.system.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.durcit.be.system.response.item.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    GET_USER_PROFILE_SUCCESS(Status.OK, Message.READ_USER),
    CREATED_USER(Status.CREATED, Message.CREATED_USER),
    NOT_FOUND_USER(Status.NOT_FOUND, Message.NOT_FOUND_USER),
    DUPLICATE_USER(Status.BAD_REQUEST, Message.DUPLICATE_USER),

    SEND_EMAIL_SUCCESS(Status.NO_CONTENT, Message.SEND_EMAIL_SUCCESS),
    PASSWORD_RESET_SUCCESS(Status.NO_CONTENT, Message.UPDATE_USER),
    VERIFY_EMAIL_SUCCESS(Status.NO_CONTENT, Message.VERIFY_EMAIL_SUCCESS),
    VERIFY_EMAIL_FAIL(Status.BAD_REQUEST, Message.VERIFY_EMAIL_FAIL),

    // 기타 성공 응답
    READ_IS_LOGIN(Status.OK, Message.READ_IS_LOGIN),
    LOGIN_SUCCESS(Status.OK, Message.LOGIN_SUCCESS),
    GET_LOGIN(Status.NO_CONTENT, Message.GET_LOGIN),
    UPDATE_PASSWORD(Status.NO_CONTENT, Message.UPDATE_PASSWORD),
    HEALTHY_SUCCESS(Status.OK, Message.HEALTHY_SUCCESS),
    REISSUE_SUCCESS(Status.OK, Message.REISSUE_SUCCESS),

    // 기타 실패 응답
    INTERNAL_SERVER_ERROR(Status.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR),
    anonymousUser_ERROR(Status.INTERNAL_SERVER_ERROR, Message.anonymousUser_ERROR),
    UNAUTHORIZED_ERROR(Status.UNAUTHORIZED, Message.UNAUTHORIZED),
    FORBIDDEN_ERROR(Status.FORBIDDEN, Message.FORBIDDEN),
    LOGIN_FAIL(Status.BAD_REQUEST, Message.LOGIN_FAIL),
    TOKEN_EXPIRED(Status.UNAUTHORIZED, Message.TOKEN_EXPIRED),
    TOKEN_ERROR(Status.UNAUTHORIZED, Message.TOKEN_ERROR),

    // post
    GET_POST_SUCCESS(Status.OK, PostMessage.GET_POST_SUCCESS),
    CREATE_POST_SUCCESS(Status.CREATED, PostMessage.CREATE_POST_SUCCESS),
    UPDATE_POST_SUCCESS(Status.NO_CONTENT, PostMessage.UPDATE_POST_SUCCESS),
    DELETE_POST_SUCCESS(Status.NO_CONTENT, PostMessage.DELETE_POST_SUCCESS),

    TOGGLE_EMOJI_SUCCESS(Status.NO_CONTENT, PostMessage.TOGGLE_EMOJI_SUCCESS),

    GET_POST_LIKES_SUCCESS(Status.OK, PostMessage.GET_POST_LIKES_SUCCESS),
    TOGGLE_LIKE_SUCCESS(Status.OK, PostMessage.TOGGLE_LIKE_SUCCESS),

    // upload
    UPLOAD_FILES_SUCCESS(Status.CREATED, PostMessage.UPLOAD_FILES_SUCCESS),
    UPDATE_FILES_SUCCESS(Status.NO_CONTENT, PostMessage.UPDATE_FILES_SUCCESS),
    UPDATE_PROFILE_IMAGE_SUCCESS(Status.NO_CONTENT, Message.UPDATE_USER),

    // member follow
    TOGGLE_MEMBER_FOLLOW_SUCCESS(Status.NO_CONTENT, FollowMessage.TOGGLE_MEMBER_FOLLOW_SUCCESS),
    GET_MEMBER_FOLLOWER_SUCCESS(Status.OK, FollowMessage.GET_MEMBER_FOLLOWER_SUCCESS),
    GET_MEMBER_FOLLOWEE_SUCCESS(Status.OK, FollowMessage.GET_MEMBER_FOLLOWEE_SUCCESS),

    // tag follow
    GET_TAG_FOLLOW_SUCCESS(Status.OK, TagFollowMessage.GET_TAG_FOLLOW_SUCCESS),
    CREATE_TAG_FOLLOW_SUCCESS(Status.CREATED, TagFollowMessage.CREATE_TAG_FOLLOW_SUCCESS),
    UPDATE_TAG_FOLLOW_SUCCESS(Status.OK, TagFollowMessage.UPDATE_TAG_FOLLOW_SUCCESS),
    DELETE_TAG_FOLLOW_SUCCESS(Status.OK, TagFollowMessage.DELETE_TAG_FOLLOW_SUCCESS),

    // postsTag
    GET_POSTS_TAG_SUCCESS(Status.OK, PostsTagMessage.GET_POSTS_TAG_SUCCESS),
    CREATE_POSTS_TAG_SUCCESS(Status.CREATED, PostsTagMessage.CREATE_POSTS_TAG_SUCCESS),
    UPDATE_POSTS_TAG_SUCCESS(Status.OK, PostsTagMessage.UPDATE_POSTS_TAG_SUCCESS),
    DELETE_POSTS_TAG_SUCCESS(Status.OK, PostsTagMessage.DELETE_POSTS_TAG_SUCCESS),

    // chat
    CREATE_CHAT_ROOM_SUCCESS(Status.OK, ChatMessage.CREATE_CHAT_ROOM_SUCCESS),
    GET_CHAT_ROOM_SUCCESS(Status.OK, ChatMessage.GET_CHAT_ROOM_SUCCESS),
    GET_CHAT_SUCCESS(Status.OK, ChatMessage.GET_CHAT_SUCCESS),

    // search
    GET_SEARCH_SUCCESS(Status.OK, SearchMessage.GET_SEARCH_SUCCESS),

    // push
    GET_PUSHS_SUCCESS(Status.OK, PushMessage.GET_PUSHS_SUCCESS),
    UPDATE_PUSH_SUCCESS(Status.NO_CONTENT, PushMessage.UPDATE_PUSH_SUCCESS),

    // admin
    GET_ADMIN_LOG_SUCCESS(Status.OK, AdminMessage.GET_ADMIN_LOG_SUCCESS),
    RECOVER_POST_SUCCESS(Status.OK, AdminMessage.RECOVER_POST_SUCCESS),


    // update nickName
    UPDATE_NICKNAME_SUCCESS(Status.OK, UpdateNicknameMessage.UPDATE_NICKNAME_SUCCESS),





    ;

    private int httpStatus;
    private String message;

}
