package org.moguri.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.moguri.security.account.domain.CustomUser;
import org.moguri.security.dto.AuthResultDTO;
import org.moguri.security.dto.UserInfoDTO;
import org.moguri.security.util.JsonResponse;
import org.moguri.security.util.JwtProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@Component
@RequiredArgsConstructor
//인증이 성공하면 LoginSuccessHandler가 호출
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;

    private AuthResultDTO makeAuthResult(CustomUser user) {
        String username = user.getUsername();
        // 토큰 생성
        String token = jwtProcessor.generateToken(username);
        // 토큰 + 사용자 기본 정보 (사용자명, ...)를 묶어서 AuthResultDTO 구성
        return new AuthResultDTO(token, UserInfoDTO.of(user.getMember()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 인증된 사용자의 정보를 담고 있는 객체
        CustomUser user = (CustomUser) authentication.getPrincipal();

        //발급된 jwt 토큰과 사용자 정보를 묶어서 반환하는 데이터 전송 객체 (AuthResultDTO)
        AuthResultDTO result = makeAuthResult(user);
        JsonResponse.send(response, result);
    }
}
// 1.onAuthenticationSuccess 호출되고 여기서 인증된 사용자 정보를 기반으로 jwt토큰이랑 AuthResultDTo 생성
// 2.생성한 dto를 json 형태로 클라이언트에게 반환