package com.bada.Backend.domain.User.entity;

import com.bada.Backend.domain.BaseEntity;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.User.dto.UserSignUpDto;
import com.bada.Backend.domain.likes.entity.Likes;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String nickname;
    private String loginId; // 아 아이디가 있구나
    private String email; //토큰과 관련있음

    private String password;

    private String address; //뭔 형식이야?

    private Boolean is_deleted;

    private String imageUrl;
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
        this.loginId = userSignUpDto.getUserId();
        this.email = userSignUpDto.getEmail();
        this.password = userSignUpDto.getPassword();
        this.address = userSignUpDto.getAddress();
    }

    //initDB에서 유저 한명 자동생성하려고 만듬(개발편의용)
    public void dummyUser(String name, String password) {
        this.nickname = name;
        this.password = password;
    }
}
