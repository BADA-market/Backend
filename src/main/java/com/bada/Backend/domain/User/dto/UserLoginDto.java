package com.bada.Backend.domain.User.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginDto {
    private String loginId; //아이디
    private String password;
}
