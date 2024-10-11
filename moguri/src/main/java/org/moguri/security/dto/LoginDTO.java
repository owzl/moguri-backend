package org.moguri.security.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
// 클라이언트에서 로그인 요청 시 전달되는 이메일과 비밀번호 정보를 캡슐화.
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {
    private String username; // 로그인 요청 데이터를 받을때
    private String password;

    public static LoginDTO of(HttpServletRequest request) throws AuthenticationException // 클라이언트가 보낸 로그인 요청의 JSON 데이터를 받아 LoginDTO 객체로 만들어주는 역할
    {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(request.getInputStream(), LoginDTO.class); // JSON == 객체
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("이메일 또는 비밀번호가 없습니다.");
        }
    }
}
