package org.moguri.member.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.common.enums.Role;
import org.moguri.member.domain.Member;

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
        // 역할이 null일 경우 기본값 "USER"로 설정
        Role finalRole = (role != null) ? role : Role.USER; // Role.USER가 기본값

        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .role(finalRole) // 최종적으로 설정된 역할을 사용
                .build();
        return member;
    }
}