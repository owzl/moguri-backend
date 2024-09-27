package org.moguri.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moguri.security.account.domain.MemberVO;

import java.util.List;
// 인증된 사용장의 정보 (이메일,닉네임,권한리스트) 담아 클라이언트로 전송
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    String email; // 사용자 정보 반환
    String nickname;
    List<String> roles;
// MemberVO 객체를 받아 사용자 정보를 추출하고, 이를 UserInfoDTO로 변환
    public static UserInfoDTO of(MemberVO member) {
        return new UserInfoDTO(
                member.getEmail(), // 이메일 사용
                member.getNickName(), // 닉네임 사용
                member.getAuthList().stream()
                        .map(a -> a.getAuth())
                        .toList()
        );
    }
}
