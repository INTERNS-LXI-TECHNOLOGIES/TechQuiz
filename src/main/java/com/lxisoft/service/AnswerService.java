package com.lxisoft.service;

import com.lxisoft.service.dto.AnswerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.domain.Answer}.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save.
     * @return the persisted entity.
     */
    AnswerDTO save(AnswerDTO answerDTO);

    /**
     * Get all the answers.
     *
     * @return the list of entities.
     */
    List<AnswerDTO> findAll();
    /**
     * Get all the AnswerDTO where Question is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AnswerDTO> findAllWhereQuestionIsNull();


    /**
     * Get the "id" answer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerDTO> findOne(Long id);

    /**
     * Delete the "id" answer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
