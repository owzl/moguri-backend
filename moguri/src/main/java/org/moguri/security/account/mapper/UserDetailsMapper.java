package org.moguri.security.account.mapper;

import org.moguri.security.account.domain.MemberVO;

public interface UserDetailsMapper {
    // username을 email로 바꿔줍니다.
    public MemberVO get(String email);
}
