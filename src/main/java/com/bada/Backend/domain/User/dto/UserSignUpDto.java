package com.bada.Backend.domain.User.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
//자체 로그인용이라서 우리 프로젝트 보면서 수정 필요
public class UserSignUpDto {
    private String userId;
    private String email;
    private String password;
    private String nickname;
    private String address;

}
