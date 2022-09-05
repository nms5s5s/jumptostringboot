package com.example.jumptostringboot.answer;

import com.example.jumptostringboot.mysite.sbb.DataNotFoundException;
import com.example.jumptostringboot.question.Question;
import com.example.jumptostringboot.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
//답변 작성
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        //dbeaver : new date
        answer.setCreateDate(new Date());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return  answer;
    }
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        }else {
            throw new DataNotFoundException("answer not found");
        }
    }
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
//답변에 추천인 저장
    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
