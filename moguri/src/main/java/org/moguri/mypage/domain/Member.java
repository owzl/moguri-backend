package org.moguri.member.domain;

import lombok.*;
import org.moguri.common.enums.Role;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member {

    private long id;
    private String email;
    private String password;
    private String nickName;
    private Role role;
    private LocalDateTime deletedAt;

}
