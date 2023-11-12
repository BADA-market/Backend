package com.bada.Backend.domain.User.service;

import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.User.entity.Role;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
//일반 로그인
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if(userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .address(userSignUpDto.getAddress())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder); //자기 패스워드 인코딩
        userRepository.save(user);


    }

}
