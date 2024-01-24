package com.bada.Backend.domain.User.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String nickname;
    private String userId; //아이디
    private String email;
    private String password;
    private String address;
    private int age;

    private String refreshToken;

}
