package org.moguri.security.account.mapper;

import org.moguri.security.account.domain.MemberVO;

public interface UserDetailsMapper {
    public MemberVO get(String username);
}
