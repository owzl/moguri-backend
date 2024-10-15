package org.moguri.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.moguri.security.account.domain.MemberVO;

import java.math.BigDecimal; // BigDecimal을 임포트합니다.
import java.util.List;

// 인증된 사용자의 정보 (이메일, 닉네임, 권한 리스트) 담아 클라이언트로 전송
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    long  memberId; // 사용자의 ID 추가
    String email; // 사용자 정보 반환
    String nickname;
    List<String> roles;
    BigDecimal cottonCandy; // BigDecimal로 타입 변경

    // MemberVO 객체를 받아 사용자 정보를 추출하고, 이를 UserInfoDTO로 변환
    public static UserInfoDTO of(MemberVO member) {
        return new UserInfoDTO(
                member.getMemberId(), // MemberVO의 ID를 사용
                member.getEmail(), // 이메일 사용
                member.getNickName(), // 닉네임 사용
                member.getAuthList().stream()
                        .map(a -> a.getAuth())
                        .toList(),
                member.getCottonCandy() // BigDecimal 타입으로 값을 가져옵니다.
        );
    }
}
