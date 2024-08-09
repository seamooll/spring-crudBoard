package com.mysite.springboard.question;

import com.mysite.springboard.answer.Answer;
import com.mysite.springboard.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT") // text를 column data로 넣을 수 있음. 글자 수 제한 X
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne // 사용자 한 명이 질문을 여러 개 작성 가능
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter; // Set -> voter의 속성값 서로 중복되지 않도록

}

//엔티티의 속성은 @Column 애너테이션을 사용하지 않더라도 테이블의 열로 인식
//테이블의 열로 인식하고 싶지 않다면 @Transient 애너테이션을 사용