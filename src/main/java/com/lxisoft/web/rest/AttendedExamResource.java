package com.lxisoft.web.rest;

import com.lxisoft.service.AttendedExamService;
import com.lxisoft.web.rest.errors.BadRequestAlertException;
import com.lxisoft.service.dto.AttendedExamDTO;

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
 * REST controller for managing {@link com.lxisoft.domain.AttendedExam}.
 */
@RestController
@RequestMapping("/api")
public class AttendedExamResource {

    private final Logger log = LoggerFactory.getLogger(AttendedExamResource.class);

    private static final String ENTITY_NAME = "attendedExam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendedExamService attendedExamService;

    public AttendedExamResource(AttendedExamService attendedExamService) {
        this.attendedExamService = attendedExamService;
    }

    /**
     * {@code POST  /attended-exams} : Create a new attendedExam.
     *
     * @param attendedExamDTO the attendedExamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendedExamDTO, or with status {@code 400 (Bad Request)} if the attendedExam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attended-exams")
    public ResponseEntity<AttendedExamDTO> createAttendedExam(@RequestBody AttendedExamDTO attendedExamDTO) throws URISyntaxException {
        log.debug("REST request to save AttendedExam : {}", attendedExamDTO);
        if (attendedExamDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendedExam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendedExamDTO result = attendedExamService.save(attendedExamDTO);
        return ResponseEntity.created(new URI("/api/attended-exams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attended-exams} : Updates an existing attendedExam.
     *
     * @param attendedExamDTO the attendedExamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendedExamDTO,
     * or with status {@code 400 (Bad Request)} if the attendedExamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendedExamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attended-exams")
    public ResponseEntity<AttendedExamDTO> updateAttendedExam(@RequestBody AttendedExamDTO attendedExamDTO) throws URISyntaxException {
        log.debug("REST request to update AttendedExam : {}", attendedExamDTO);
        if (attendedExamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendedExamDTO result = attendedExamService.save(attendedExamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendedExamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attended-exams} : get all the attendedExams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendedExams in body.
     */
    @GetMapping("/attended-exams")
    public List<AttendedExamDTO> getAllAttendedExams() {
        log.debug("REST request to get all AttendedExams");
        return attendedExamService.findAll();
    }

    /**
     * {@code GET  /attended-exams/:id} : get the "id" attendedExam.
     *
     * @param id the id of the attendedExamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendedExamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attended-exams/{id}")
    public ResponseEntity<AttendedExamDTO> getAttendedExam(@PathVariable Long id) {
        log.debug("REST request to get AttendedExam : {}", id);
        Optional<AttendedExamDTO> attendedExamDTO = attendedExamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendedExamDTO);
    }

    /**
     * {@code DELETE  /attended-exams/:id} : delete the "id" attendedExam.
     *
     * @param id the id of the attendedExamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attended-exams/{id}")
    public ResponseEntity<Void> deleteAttendedExam(@PathVariable Long id) {
        log.debug("REST request to delete AttendedExam : {}", id);
        attendedExamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
