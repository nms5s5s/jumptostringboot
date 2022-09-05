package com.example.jumptostringboot.question;


import com.example.jumptostringboot.answer.Answer;
import com.example.jumptostringboot.mysite.sbb.DataNotFoundException;
import com.example.jumptostringboot.user.SiteUser;
import lombok.RequiredArgsConstructor;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.MapUtils;


import javax.persistence.criteria.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class QuestionService {
        private final QuestionRepository questionRepository;
    //-----------검색----------------//

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
//----------------------질문 목록 & 검색----------------------//
        public Page<Question> getList(int page, String kw){

            List<Sort.Order> sorts = new ArrayList<>();

            sorts.add(Sort.Order.desc("createDate"));

            Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));

            Specification<Question> spec = search(kw);

            return this.questionRepository.findAll(spec, pageable);
        }
//----------질문 상세보기 & 조회수추가-------------//
        public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            //조회수
            Question question1 = question.get();
            question1.setCountview(question1.getCountview() + 1);
            this.questionRepository.save(question1);
            return question1;
            //----------------
        } else {
        //만약 id 값에 해당하는 Question 데이터가 없을 경우에는 DataNotFoundException을 발생
            throw new DataNotFoundException("question not found");
        }
    }
//-----------------질문 작성하기 & 파일첨부----------------//
        public void create(String subject, String content, SiteUser user, MultipartFile file) throws Exception {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            file.transferTo(saveFile);

            Question q = new Question();
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(new Date());
            q.setAuthor(user);
            q.setFilename(fileName);
            q.setFilepath("/files/" + fileName);
            this.questionRepository.save(q);
        }
    //------------------질문 수정------------------//
        public void modify(Question question, String subject, String content)  {


            question.setSubject(subject);
            question.setContent(content);
            question.setModifyDate(LocalDateTime.now());
            this.questionRepository.save(question);
        }
//-----------삭제 기능-----------//
        public void delete(Question question) {

        this.questionRepository.delete(question);
        }
//-------------추천--------------//
        public void vote(Question question, SiteUser siteUser) {
            question.getVoter().add(siteUser);
            this.questionRepository.save(question);
    }

}
