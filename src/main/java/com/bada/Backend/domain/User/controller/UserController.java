package com.bada.Backend.domain.User.controller;

import com.bada.Backend.domain.User.dto.UserLoginDto;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 성공 여부를 반환합니다.")
    @PostMapping("/sign_up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @Operation(summary = "로그인", description = "로그인 성공 여부를 반환합니다.")
    @PostMapping("/login")
    public Long login(@RequestBody UserLoginDto userloginDto) throws Exception {
        return userService.login(userloginDto);

    }
}
