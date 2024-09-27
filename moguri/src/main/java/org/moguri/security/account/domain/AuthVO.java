package org.moguri.security.account.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
//권한 정보 관리
@Data
public class AuthVO implements GrantedAuthority {
    private String username; // admin
    private String auth; // ROLE_MEMBER
    private String MemberId;
    // 권한 정보를 반환

    @Override
    public String getAuthority() {
        return auth;
    }
}