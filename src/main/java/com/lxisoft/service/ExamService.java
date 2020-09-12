package com.lxisoft.service;

import com.lxisoft.service.dto.ExamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.domain.Exam}.
 */
public interface ExamService {
	

    /**
     * Save a exam.
     *
     * @param examDTO the entity to save.
     * @return the persisted entity.
     */
    ExamDTO save(ExamDTO examDTO);

    /**
     * Get all the exams.
     *
     * @return the list of entities.
     */
    List<ExamDTO> findAll();

    /**
     * Get all the exams with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ExamDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" exam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamDTO> findOne(Long id);

    /**
     * Delete the "id" exam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
