package com.lxisoft.service;

import com.lxisoft.domain.Question;
import com.lxisoft.repository.QuestionRepository;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionRepository questionRepository;

    public void saveQuestion(Question question)
    {
        questionRepository.save(question);
    }
/*    public Question get(long id)
    {
        return questionRepository.getOne(id);
    }

    public List<Question> getAll()
    {

        return  questionRepository.findAll();
    }

    public long deleteById(long id)
    {
        questionRepository.deleteById(id);
        return id;
    }
*/
    
    public List<Question> getAll()
    {

        return  questionRepository.findAll();
    }

    
//    public List<Question> findAll() {
//		List<Question> question=questionRepository.findAll();
//		return question;
//	}
}
