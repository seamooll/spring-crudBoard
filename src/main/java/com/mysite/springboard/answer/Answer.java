package com.mysite.springboard.answer;

import com.mysite.springboard.question.Question;
import com.mysite.springboard.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.time.LocalDateTime;

@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // Question Entity와 연결. 부모자식 갖는 구조에서 사용.
    private Question question;

    @ManyToOne
    private SiteUser author; // 글쓴이의 ID 값 저장됨. 질문 데이터 저장 시 글쓴이도 함께 저장.

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;
}
