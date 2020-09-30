package com.lxisoft.service.impl;

import com.lxisoft.service.AnswerService;


import com.lxisoft.domain.Answer;
import com.lxisoft.domain.Question;
import com.lxisoft.repository.AnswerRepository;
import com.lxisoft.service.dto.AnswerDTO;
import com.lxisoft.service.mapper.AnswerMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Answer}.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.toEntity(answerDTO);
        answer = answerRepository.save(answer);
        return answerMapper.toDto(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDTO> findAll() {
        log.debug("Request to get all Answers");
        return answerRepository.findAll().stream()
            .map(answerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the answers where Question is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AnswerDTO> findAllWhereQuestionIsNull() {
        log.debug("Request to get all answers where Question is null");
        return StreamSupport
            .stream(answerRepository.findAll().spliterator(), false)
            .filter(answer -> answer.getQuestion() == null)
            .map(answerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerDTO> findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        return answerRepository.findById(id)
            .map(answerMapper::toDto);
    }

    
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.deleteById(id);
    }
    
    public void saveAnswer(Answer answer)
    {
    	answerRepository.save(answer);
    }
    public Answer get(long id)
    {
        return answerRepository.getOne(id);
    }
}
