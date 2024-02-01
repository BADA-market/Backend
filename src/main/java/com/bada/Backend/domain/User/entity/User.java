package com.bada.Backend.domain.User.entity;

import com.bada.Backend.domain.BaseEntity;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.User.Role;
import com.bada.Backend.domain.User.SocialType;
import com.bada.Backend.domain.User.dto.OAuth2ExtraInfoDTO;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.likes.entity.Likes;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email; //토큰과 관련있음
    private String password;
    private String nickname;
    private String imageUrl;
    private String loginId; // 아 아이디가 있구나

    private String city;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    private Boolean is_deleted;


    @OneToMany(mappedBy = "seller")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    //User와 Item의 관계 1대N
    //연관관계의 주인은 N쪽인 Item
    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user") //mappedBy 뒤에 나오는게 뭐지?? 겹치는게 불편한데..
    private List<Likes> likes = new ArrayList<>();




    @Builder
    public User(UserSignUpDto userSignUpDto) {
        this.nickname = userSignUpDto.getNickname();
        this.loginId = userSignUpDto.getLoginId();
        this.email = userSignUpDto.getEmail();
        this.password = userSignUpDto.getPassword();
        this.city = userSignUpDto.getAddress(); //이름 변경 (address -> city)
        //this.age = userSignUpDto.getAge(); //추가
        this.role = Role.USER;
        this.refreshToken = ""; //null값을 넣고 싶은데
    }


    public User(SocialType socialType, String socialId, String email, String nickname, String imageUrl, Role role) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.email = email;
        this.role = role;
    }

    //initDB에서 유저 한명 자동생성하려고 만듬(개발편의용)
    public void dummyUser(String name, String password) {
        this.nickname = name;
        this.password = password;
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    //소셜 로그인 추가 정보 저장하기 위한 로직
    public void updateSocialInfo(OAuth2ExtraInfoDTO oAuth2ExtraInfoDTO) {
        this.nickname = oAuth2ExtraInfoDTO.getNickname();
        this.city = oAuth2ExtraInfoDTO.getCity();
        this.email = oAuth2ExtraInfoDTO.getEmail();
        this.role = Role.USER; //User로 업그레이드
        this.is_deleted = false;
    }
}
