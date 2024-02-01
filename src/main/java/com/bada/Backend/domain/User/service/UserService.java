package com.bada.Backend.domain.User.service;

import com.bada.Backend.domain.User.Role;
import com.bada.Backend.domain.User.dto.OAuth2ExtraInfoDTO;
import com.bada.Backend.domain.User.dto.UserLoginDto;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.jwt.service.JwtService;
import com.bada.Backend.domain.User.oauth2.CustomOAuth2User;
import com.bada.Backend.domain.User.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if(userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                        .userSignUpDto(userSignUpDto)
                        .build();
        user.passwordEncode(passwordEncoder);
        log.info("LoginId : {}", user.getLoginId());

        userRepository.save(user);


    }

    public String login(UserLoginDto userLoginDto, HttpServletRequest request) throws Exception {
        String userId = userLoginDto.getLoginId();
        log.info("userId : {}", userId);
        String password = userLoginDto.getPassword();
        Optional<User> findUser = userRepository.findByLoginIdAndPassword(userLoginDto.getLoginId(), userLoginDto.getPassword());
        if(findUser.isPresent()){
            String access = jwtService.extractAccessToken(request).get();
            return access;
        }
        else { //없으면 예외
            throw new Exception("아이디와 비밀번호를 확인해 주세요");
        }
    }
    @Transactional
    public void OAuth2AddInfo(OAuth2ExtraInfoDTO oAuth2ExtraInfoDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //쿠키를 통해 Token 가져오기
        Cookie[] cookies = request.getCookies();
        String tokenValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    tokenValue = cookie.getValue();
                }
            }
        }
        log.info("tokenValue : {}", tokenValue);
        //쿠키를 통해 가져온 Token을 통해 이메일 가져오기
        String email = jwtService.extractEmail(tokenValue).get();
        log.info("email : {}", email);
        //이메일을 통해 User 찾기
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));

        //User에 추가 정보 입력하기
        findUser.updateSocialInfo(oAuth2ExtraInfoDTO);

        //User 저장하기
        userRepository.save(findUser);

        //토큰을 헤더에 추가하기
        response.addHeader(jwtService.getAccessHeader(), tokenValue);

    }

    public Boolean loginIdCheck(String loginId){
        return !userRepository.findByLoginId(loginId).isPresent();
    }

}
