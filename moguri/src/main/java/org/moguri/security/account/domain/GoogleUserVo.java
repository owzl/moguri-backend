package org.moguri.security.account.domain;

import lombok.Data;

public class GoogleUserVo {
    private Long id; // 사용자 ID (PK)
    private String email; // 구글 이메일
    private String name; // 구글 사용자 이름
    private String picture; // 구글 사용자 프로필 사진 URL
}
