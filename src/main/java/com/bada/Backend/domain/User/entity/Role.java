package com.bada.Backend.domain.User.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    //인증 시 사용하는 UserDetails의 User 객체의 role이 "ROLE_"로 시작해야 한다.
    GUEST("ROLE_GUEST"), USER("ROLE_USER");
    private final String key;
}
