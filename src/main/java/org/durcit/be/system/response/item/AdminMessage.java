package org.durcit.be.system.response.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminMessage {

    public static String GET_ADMIN_LOG_SUCCESS = "SUCCESS - 로그 조회 성공";
    public static String RECOVER_POST_SUCCESS = "SUCCESS - 게시물 복구 성공";

}
