package org.moguri.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// 인증이 성공한 후 클라이언트에 반환되는 데이터들 캡슐화
// 사용자가 로그인한 후 서버는 이 객체를 만들어 클라이어느에 토큰과 사용자 정보 전송
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResultDTO {
    String token;  // 인증 성공 시 생성된 jwt 토큰
    UserInfoDTO user; // 인증된 사용자의 정보
}