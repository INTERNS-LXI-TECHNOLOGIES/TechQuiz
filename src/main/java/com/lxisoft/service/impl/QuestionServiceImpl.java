package com.lxisoft.service.impl;

import com.lxisoft.service.QuestionService;
import com.lxisoft.domain.Question;
import com.lxisoft.repository.QuestionRepository;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll().stream()
            .map(questionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id)
            .map(questionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }
    public Question get(long id)
    {
        return questionRepository.getOne(id);
    }
    public long deleteById(long id)
    {
    	questionRepository.deleteById(id);
    	return id;
    }

     

    public void saveQuestionWithEnity(Question question) {
        log.debug("Request to save question with entity : {}", question);
        questionRepository.save(question);
    }

    public List<Question> getAll()
    {
        return  questionRepository.findAll();
    }

    public void saveQuestion(Question question)
    {
    	questionRepository.save(question);
    }

    /*public void saveQuestionWithEnity(Question question) {
    	log.debug("Request to save question with entity : {}", question);
    	questionRepository.save(question);
    }*/
}
