package com.lxisoft.web.rest;

import com.lxisoft.TechQuizApp;
import com.lxisoft.domain.Exam;
import com.lxisoft.repository.ExamRepository;
import com.lxisoft.service.ExamService;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.mapper.ExamMapper;

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

import com.lxisoft.domain.enumeration.ExamLevel;
/**
 * Integration tests for the {@link ExamResource} REST controller.
 */
@SpringBootTest(classes = TechQuizApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ExamLevel DEFAULT_LEVEL = ExamLevel.EASY;
    private static final ExamLevel UPDATED_LEVEL = ExamLevel.MEDIUM;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamService examService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamMockMvc;

    private Exam exam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exam createEntity(EntityManager em) {
        Exam exam = new Exam()
            .count(DEFAULT_COUNT)
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL);
        return exam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exam createUpdatedEntity(EntityManager em) {
        Exam exam = new Exam()
            .count(UPDATED_COUNT)
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL);
        return exam;
    }

    @BeforeEach
    public void initTest() {
        exam = createEntity(em);
    }

    @Test
    @Transactional
    public void createExam() throws Exception {
        int databaseSizeBeforeCreate = examRepository.findAll().size();
        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);
        restExamMockMvc.perform(post("/api/exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isCreated());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeCreate + 1);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testExam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExam.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createExamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examRepository.findAll().size();

        // Create the Exam with an existing ID
        exam.setId(1L);
        ExamDTO examDTO = examMapper.toDto(exam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamMockMvc.perform(post("/api/exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExams() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the examList
        restExamMockMvc.perform(get("/api/exams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exam.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get the exam
        restExamMockMvc.perform(get("/api/exams/{id}", exam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exam.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingExam() throws Exception {
        // Get the exam
        restExamMockMvc.perform(get("/api/exams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Update the exam
        Exam updatedExam = examRepository.findById(exam.getId()).get();
        // Disconnect from session so that the updates on updatedExam are not directly saved in db
        em.detach(updatedExam);
        updatedExam
            .count(UPDATED_COUNT)
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL);
        ExamDTO examDTO = examMapper.toDto(updatedExam);

        restExamMockMvc.perform(put("/api/exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isOk());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
        Exam testExam = examList.get(examList.size() - 1);
        assertThat(testExam.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testExam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExam.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingExam() throws Exception {
        int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Create the Exam
        ExamDTO examDTO = examMapper.toDto(exam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamMockMvc.perform(put("/api/exams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exam in the database
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        int databaseSizeBeforeDelete = examRepository.findAll().size();

        // Delete the exam
        restExamMockMvc.perform(delete("/api/exams/{id}", exam.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exam> examList = examRepository.findAll();
        assertThat(examList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
