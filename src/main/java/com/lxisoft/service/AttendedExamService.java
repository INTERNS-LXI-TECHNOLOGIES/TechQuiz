package com.lxisoft.service;

import com.lxisoft.service.dto.AttendedExamDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.domain.AttendedExam}.
 */
public interface AttendedExamService {

    /**
     * Save a attendedExam.
     *
     * @param attendedExamDTO the entity to save.
     * @return the persisted entity.
     */
    AttendedExamDTO save(AttendedExamDTO attendedExamDTO);

    /**
     * Get all the attendedExams.
     *
     * @return the list of entities.
     */
    List<AttendedExamDTO> findAll();


    /**
     * Get the "id" attendedExam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendedExamDTO> findOne(Long id);

    /**
     * Delete the "id" attendedExam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
