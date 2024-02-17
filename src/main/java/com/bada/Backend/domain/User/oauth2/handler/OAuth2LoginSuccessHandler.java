package com.bada.Backend.domain.User.oauth2.handler;

import com.bada.Backend.domain.User.Role;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.jwt.service.JwtService;
import com.bada.Backend.domain.User.oauth2.CustomOAuth2User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                //쿠키 만들기
                Cookie token = new Cookie("token", accessToken);
                token.setHttpOnly(false);
                token.setSecure(false);
                token.setMaxAge(3600);
                token.setPath("/");
                response.addCookie(token);

                //리프레쉬 토큰 -> 쿠키 만들기
                String refreshToken = jwtService.createRefreshToken();
                //이 코드 의미가 없어 어차피 쿠키로 보내는거니까, 하지만 삭제 보류
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
                response.sendRedirect("/oauth2/sign_up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트, /login/oauth2/code/naver
                log.info("회원가입 추가 정보 입력 폼으로 리다이렉트");

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                User findUser = userRepository.findByEmail(oAuth2User.getEmail())
                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
                findUser.authorizeUser();

                // User PK를 JSON 객체로 변환
                Map<String, String> userKeyMap = new HashMap<>();
                userKeyMap.put("userKey", findUser.getId().toString());

                // JSON 문자열로 변환
                String userKeyJson = objectMapper.writeValueAsString(userKeyMap);

                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding("UTF-8");

                response.setContentType("application/json;charset=UTF-8"); // 컨텐트 타입을 application/json으로 설정
                response.getWriter().write(userKeyJson); // JSON 문자열 응답으로 반환
            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
