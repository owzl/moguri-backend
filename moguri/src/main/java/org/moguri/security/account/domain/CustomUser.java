package org.moguri.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User { // 확장
    private MemberVO member; // 실질적인 사용자 데이터

    // username을 email로 대체
    public CustomUser(String email, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }

    // MemberVO를 이용한 생성자
    public CustomUser(MemberVO vo) {
        super(vo.getEmail(), vo.getPassword(), vo.getAuthList()); // email 사용
        this.member = vo;
    }

    // 사용자 ID 반환
    public long getId() {
        return member.getMemberId(); // MemberVO에서 ID 반환
    }
}
