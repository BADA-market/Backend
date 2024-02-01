package com.bada.Backend.domain.User.controller;

import com.bada.Backend.domain.User.dto.OAuth2ExtraInfoDTO;
import com.bada.Backend.domain.User.dto.UserLoginDto;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.User.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping("/sign_up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto userloginDto, HttpServletRequest request) throws Exception{
//        String access = userService.login(userloginDto, request);
//        Map<String, Object> response = new HashMap<>();
//        response.put("access", access);
//        return ResponseEntity.ok(response);
//
//    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/oauth2/sign_up")
    public String OauthNaverRedirect() {
        log.info("네이버 로그인 요청");
        return "extraSocial.html";
    }

    @PostMapping("/oauth2/extra_info")
    public String OauthExtraInfo(@RequestBody OAuth2ExtraInfoDTO oAuth2ExtraInfoDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("추가 정보 입력 요청");
        userService.OAuth2AddInfo(oAuth2ExtraInfoDTO, request, response);
        return "추가 정보 입력 성공";
    }
    @PostMapping("/user/loginId_check/{loginId}")
    public Boolean loginIdCheck(@PathVariable String loginId){
        log.info("loginIdCheck 요청");
        return userService.loginIdCheck(loginId);
    }

}
