package com.bada.Backend.domain.User.entity;

import com.bada.Backend.domain.BaseEntity;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Item.entity.Item;
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

    private String password;

    private String address; //뭔 형식이야?

    private Boolean is_deleted;

    private String email;

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
    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
