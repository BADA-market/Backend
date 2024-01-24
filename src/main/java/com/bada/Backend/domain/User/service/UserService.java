package com.bada.Backend.domain.User.service;

import com.bada.Backend.domain.User.Role;
import com.bada.Backend.domain.User.dto.UserLoginDto;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.jwt.service.JwtService;
import com.bada.Backend.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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

        userRepository.save(user);


    }

    public Long login(UserLoginDto userLoginDto, HttpServletRequest request) throws Exception {
        String userId = userLoginDto.getLoginId();
        String password = userLoginDto.getPassword();
        Optional<User> findUser = userRepository.findByLoginIdAndPassword(userLoginDto.getLoginId(), userLoginDto.getPassword());
        if(findUser.isPresent()){
            return findUser.get().getId(); //있으면 id(기본키) 반환
        }
        else { //없으면 예외
            throw new Exception("아이디와 비밀번호를 확인해 주세요");
        }




    }

}
