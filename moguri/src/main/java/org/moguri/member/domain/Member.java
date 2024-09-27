package org.moguri.member.domain;

import lombok.*;
import org.moguri.common.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member {

    private long id;                // 회원 ID
    private String email;           // 이메일 (ID)
    private String password;        // 비밀번호
    private String nickName;        // 닉네임
    private Role role;              // 역할 (e.g. USER, ADMIN)
    private LocalDateTime deletedAt; // 삭제 시점
    private LocalDateTime createdAt; // 생성 시점
    private LocalDateTime updatedAt; // 수정 시점

}
