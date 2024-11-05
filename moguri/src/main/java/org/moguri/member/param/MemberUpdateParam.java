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
    private Long id; // 업데이트할 멤버의 ID
    private String email; // 추가
    private String password;
    private String nickName;
    private int cottonCandy;
}