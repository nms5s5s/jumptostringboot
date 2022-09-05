package com.example.jumptostringboot.answer;

import com.example.jumptostringboot.question.Question;
import com.example.jumptostringboot.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

//답변 엔티티 생성
@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String content;

    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;
}
