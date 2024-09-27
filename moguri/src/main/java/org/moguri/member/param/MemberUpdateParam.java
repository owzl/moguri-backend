package org.moguri.member.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberUpdateParam {

    private String password;  // 비밀번호 업데이트
    private String nickName;  // 닉네임 업데이트
}
