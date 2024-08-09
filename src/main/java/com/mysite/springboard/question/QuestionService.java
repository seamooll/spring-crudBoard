package com.mysite.springboard.question;

import com.mysite.springboard.DataNotFoundException;
import com.mysite.springboard.answer.Answer;
import com.mysite.springboard.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /*public List<Question> getList(){
        return questionRepository.findAll();
    }*/
    // 자바. List(순서있는 요소들의 집합. 중복요소 허용) 인터페이스 구현 객체인 ArrayList 객체 생성하고 이를 sorts 변수에 할당.
    // 이 리스트는 Sort.Order 타입의 요소를 담도록 정의되어 있음.
    // Sort.Order은 Spring Data Jpa에서 정렬 조건 정의하는 클래스. 데이터 베이스 쿼리를 수행할 때 결과를 정렬하기 위해 사용.


    private Specification<Question> search(String kw) {
        return new Specification<>(){
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.))
            }

        }
    }

    public Page<Question> getList(int page) { //정수타입 페이지 번호 입력, 해당 페이지의 Page 객체 리턴.
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public void modify(Question question,String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    // 로그인한 사용자를 질문 엔터티에 추천인으로 저장
    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);

    }

}
