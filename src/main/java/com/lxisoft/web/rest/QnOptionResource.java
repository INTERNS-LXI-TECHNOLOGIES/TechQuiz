package com.lxisoft.web.rest;

import com.lxisoft.service.QnOptionService;
import com.lxisoft.web.rest.errors.BadRequestAlertException;
import com.lxisoft.service.dto.QnOptionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lxisoft.domain.QnOption}.
 */
@RestController
@RequestMapping("/api")
public class QnOptionResource {

    private final Logger log = LoggerFactory.getLogger(QnOptionResource.class);

    private static final String ENTITY_NAME = "qnOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QnOptionService qnOptionService;

    public QnOptionResource(QnOptionService qnOptionService) {
        this.qnOptionService = qnOptionService;
    }

    /**
     * {@code POST  /qn-options} : Create a new qnOption.
     *
     * @param qnOptionDTO the qnOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qnOptionDTO, or with status {@code 400 (Bad Request)} if the qnOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/qn-options")
    public ResponseEntity<QnOptionDTO> createQnOption(@RequestBody QnOptionDTO qnOptionDTO) throws URISyntaxException {
        log.debug("REST request to save QnOption : {}", qnOptionDTO);
        if (qnOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new qnOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QnOptionDTO result = qnOptionService.save(qnOptionDTO);
        return ResponseEntity.created(new URI("/api/qn-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qn-options} : Updates an existing qnOption.
     *
     * @param qnOptionDTO the qnOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qnOptionDTO,
     * or with status {@code 400 (Bad Request)} if the qnOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qnOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/qn-options")
    public ResponseEntity<QnOptionDTO> updateQnOption(@RequestBody QnOptionDTO qnOptionDTO) throws URISyntaxException {
        log.debug("REST request to update QnOption : {}", qnOptionDTO);
        if (qnOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QnOptionDTO result = qnOptionService.save(qnOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qnOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qn-options} : get all the qnOptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qnOptions in body.
     */
    @GetMapping("/qn-options")
    public List<QnOptionDTO> getAllQnOptions() {
        log.debug("REST request to get all QnOptions");
        return qnOptionService.findAll();
    }

    /**
     * {@code GET  /qn-options/:id} : get the "id" qnOption.
     *
     * @param id the id of the qnOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qnOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/qn-options/{id}")
    public ResponseEntity<QnOptionDTO> getQnOption(@PathVariable Long id) {
        log.debug("REST request to get QnOption : {}", id);
        Optional<QnOptionDTO> qnOptionDTO = qnOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qnOptionDTO);
    }

    /**
     * {@code DELETE  /qn-options/:id} : delete the "id" qnOption.
     *
     * @param id the id of the qnOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/qn-options/{id}")
    public ResponseEntity<Void> deleteQnOption(@PathVariable Long id) {
        log.debug("REST request to delete QnOption : {}", id);
        qnOptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
