package com.bada.Backend.domain.User.entity;

import com.bada.Backend.domain.Item.entity.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
// @AllArgsConstructor 필요한가??
@Table(name = "USERS") //h2이면 user로 했을 때 문제가 있을 수 있다네
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String loginId; // 아 아이디가 있구나
    private String email; //토큰과 관련있음
    private String password;

    private String nickname;
    private String imageUrl;
    private String address; //뭔 형식이야?
    private Boolean is_deleted;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; //KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값(일반 로그인인 경우 null)

    private String refreshToken; // 그냥 토큰은 저장 안하네

    //User와 Item의 관계 1대N
    //연관관계의 주인은 N쪽인 Item
    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @Builder
    public User(String nickname, String password, String email, Role role, String address, String loginId,
                SocialType socialType, String imageUrl, String socialId) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.address = address;
        this.loginId = loginId;
        this.socialType = socialType;
        this.imageUrl = imageUrl; // 또는 다른 필드 추가
        this.socialId = socialId;
    }




    //유저 권한 설정 메소드 Guest에서 USER로 떡상
    //GUEST 소셜로그인 했는데 그 외 정보 입력 안 한 상태야
    public void authorizeUser() {
        this.role = Role.USER;
    }

    //비밀번호를 인코딩해서 저장하나봐
    public void passwordEncode(PasswordEncoder passwordEncoder) {
         this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }


}
