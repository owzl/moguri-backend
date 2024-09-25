package org.moguri.member.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.common.enums.Role;
import org.moguri.member.domain.Member;

import static org.moguri.member.domain.CottonCandyConst.INITIAL_COTTON_CANDY;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberCreateParam {

    private String email; // id

    private String password;

    private String nickName;

    private Role role;

    public Member toEntity() {
        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .role(Role.USER)
                .cottonCandy(INITIAL_COTTON_CANDY)
                .build();
        return member;
    }
}
