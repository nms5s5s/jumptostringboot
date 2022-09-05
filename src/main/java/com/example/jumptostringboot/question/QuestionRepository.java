package com.example.jumptostringboot.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import java.io.File;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
        Question findBySubject(String subject);
        Question findBySubjectAndContent(String subject, String content);
        List<Question> findBySubjectLike(String subject);

//        Pageable 객체를 입력으로 받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드를 생성
        Page<Question> findAllBy(Pageable pageable);
        Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
