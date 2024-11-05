package org.moguri.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.moguri.security.account.domain.CustomUser;
import org.moguri.security.account.domain.MemberVO;
import org.moguri.security.account.mapper.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberVO vo = mapper.get(email); // email로 변경
        if (vo == null) {
            throw new UsernameNotFoundException(email + "은 없는 이메일입니다."); // 메시지도 변경
        }
        return new CustomUser(vo);
    }
}