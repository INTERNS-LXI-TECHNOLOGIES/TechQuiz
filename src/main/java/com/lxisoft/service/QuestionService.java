package com.lxisoft.service;

import com.lxisoft.domain.Question;
import com.lxisoft.service.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * Service Interface for managing {@link com.lxisoft.domain.Question}.
 */
public interface QuestionService {
	 final Logger log = LoggerFactory.getLogger(QuestionService.class);
	   
	     

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save.
     * @return the persisted entity.
     */
    QuestionDTO save(QuestionDTO questionDTO);

    /**
     * Get all the questions.
     *
     * @return the list of entities.
     */
     List<QuestionDTO> findAll();
    
    /**
     * Get the "id" question.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionDTO> findOne(Long id);

    /**
     * Delete the "id" question.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
