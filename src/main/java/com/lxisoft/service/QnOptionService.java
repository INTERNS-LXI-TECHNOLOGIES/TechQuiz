package com.lxisoft.service;

import com.lxisoft.service.dto.QnOptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.domain.QnOption}.
 */
public interface QnOptionService {

    /**
     * Save a qnOption.
     *
     * @param qnOptionDTO the entity to save.
     * @return the persisted entity.
     */
    QnOptionDTO save(QnOptionDTO qnOptionDTO);

    /**
     * Get all the qnOptions.
     *
     * @return the list of entities.
     */
    List<QnOptionDTO> findAll();


    /**
     * Get the "id" qnOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QnOptionDTO> findOne(Long id);

    /**
     * Delete the "id" qnOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
