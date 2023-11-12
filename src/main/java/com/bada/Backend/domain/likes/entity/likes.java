package com.bada.Backend.domain.likes.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(schema = "likes")
public class likes {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;
}
