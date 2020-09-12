package com.lxisoft.web.rest;

import com.lxisoft.TechQuizApp;
import com.lxisoft.domain.AttendedExam;
import com.lxisoft.repository.AttendedExamRepository;
import com.lxisoft.service.AttendedExamService;
import com.lxisoft.service.dto.AttendedExamDTO;
import com.lxisoft.service.mapper.AttendedExamMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AttendedExamResource} REST controller.
 */
@SpringBootTest(classes = TechQuizApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendedExamResourceIT {

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Boolean DEFAULT_RESULT = false;
    private static final Boolean UPDATED_RESULT = true;

    @Autowired
    private AttendedExamRepository attendedExamRepository;

    @Autowired
    private AttendedExamMapper attendedExamMapper;

    @Autowired
    private AttendedExamService attendedExamService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendedExamMockMvc;

    private AttendedExam attendedExam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendedExam createEntity(EntityManager em) {
        AttendedExam attendedExam = new AttendedExam()
            .total(DEFAULT_TOTAL)
            .result(DEFAULT_RESULT);
        return attendedExam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendedExam createUpdatedEntity(EntityManager em) {
        AttendedExam attendedExam = new AttendedExam()
            .total(UPDATED_TOTAL)
            .result(UPDATED_RESULT);
        return attendedExam;
    }

    @BeforeEach
    public void initTest() {
        attendedExam = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendedExam() throws Exception {
        int databaseSizeBeforeCreate = attendedExamRepository.findAll().size();
        // Create the AttendedExam
        AttendedExamDTO attendedExamDTO = attendedExamMapper.toDto(attendedExam);
        restAttendedExamMockMvc.perform(post("/api/attended-exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendedExamDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendedExam in the database
        List<AttendedExam> attendedExamList = attendedExamRepository.findAll();
        assertThat(attendedExamList).hasSize(databaseSizeBeforeCreate + 1);
        AttendedExam testAttendedExam = attendedExamList.get(attendedExamList.size() - 1);
        assertThat(testAttendedExam.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testAttendedExam.isResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createAttendedExamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendedExamRepository.findAll().size();

        // Create the AttendedExam with an existing ID
        attendedExam.setId(1L);
        AttendedExamDTO attendedExamDTO = attendedExamMapper.toDto(attendedExam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendedExamMockMvc.perform(post("/api/attended-exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendedExamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendedExam in the database
        List<AttendedExam> attendedExamList = attendedExamRepository.findAll();
        assertThat(attendedExamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendedExams() throws Exception {
        // Initialize the database
        attendedExamRepository.saveAndFlush(attendedExam);

        // Get all the attendedExamList
        restAttendedExamMockMvc.perform(get("/api/attended-exams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendedExam.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAttendedExam() throws Exception {
        // Initialize the database
        attendedExamRepository.saveAndFlush(attendedExam);

        // Get the attendedExam
        restAttendedExamMockMvc.perform(get("/api/attended-exams/{id}", attendedExam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendedExam.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAttendedExam() throws Exception {
        // Get the attendedExam
        restAttendedExamMockMvc.perform(get("/api/attended-exams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendedExam() throws Exception {
        // Initialize the database
        attendedExamRepository.saveAndFlush(attendedExam);

        int databaseSizeBeforeUpdate = attendedExamRepository.findAll().size();

        // Update the attendedExam
        AttendedExam updatedAttendedExam = attendedExamRepository.findById(attendedExam.getId()).get();
        // Disconnect from session so that the updates on updatedAttendedExam are not directly saved in db
        em.detach(updatedAttendedExam);
        updatedAttendedExam
            .total(UPDATED_TOTAL)
            .result(UPDATED_RESULT);
        AttendedExamDTO attendedExamDTO = attendedExamMapper.toDto(updatedAttendedExam);

        restAttendedExamMockMvc.perform(put("/api/attended-exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendedExamDTO)))
            .andExpect(status().isOk());

        // Validate the AttendedExam in the database
        List<AttendedExam> attendedExamList = attendedExamRepository.findAll();
        assertThat(attendedExamList).hasSize(databaseSizeBeforeUpdate);
        AttendedExam testAttendedExam = attendedExamList.get(attendedExamList.size() - 1);
        assertThat(testAttendedExam.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testAttendedExam.isResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendedExam() throws Exception {
        int databaseSizeBeforeUpdate = attendedExamRepository.findAll().size();

        // Create the AttendedExam
        AttendedExamDTO attendedExamDTO = attendedExamMapper.toDto(attendedExam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendedExamMockMvc.perform(put("/api/attended-exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendedExamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendedExam in the database
        List<AttendedExam> attendedExamList = attendedExamRepository.findAll();
        assertThat(attendedExamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendedExam() throws Exception {
        // Initialize the database
        attendedExamRepository.saveAndFlush(attendedExam);

        int databaseSizeBeforeDelete = attendedExamRepository.findAll().size();

        // Delete the attendedExam
        restAttendedExamMockMvc.perform(delete("/api/attended-exams/{id}", attendedExam.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendedExam> attendedExamList = attendedExamRepository.findAll();
        assertThat(attendedExamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
