package com.mysite.springboard.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser { //스프링 시큐리티에 이미 User 클래스 존재. 패키지가 달라 사용은 할 수 있으나 혹시나

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 중복X, DB에 unique index로 생성됨. UK(unique key)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}

