package com.lxisoft.web.rest;

import com.lxisoft.TechQuizApp;
import com.lxisoft.domain.QnOption;
import com.lxisoft.repository.QnOptionRepository;
import com.lxisoft.service.QnOptionService;
import com.lxisoft.service.dto.QnOptionDTO;
import com.lxisoft.service.mapper.QnOptionMapper;

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
 * Integration tests for the {@link QnOptionResource} REST controller.
 */
@SpringBootTest(classes = TechQuizApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QnOptionResourceIT {

    private static final String DEFAULT_OPTION = "AAAAAAAAAA";
    private static final String UPDATED_OPTION = "BBBBBBBBBB";

    @Autowired
    private QnOptionRepository qnOptionRepository;

    @Autowired
    private QnOptionMapper qnOptionMapper;

    @Autowired
    private QnOptionService qnOptionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQnOptionMockMvc;

    private QnOption qnOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QnOption createEntity(EntityManager em) {
        QnOption qnOption = new QnOption()
            .option(DEFAULT_OPTION);
        return qnOption;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QnOption createUpdatedEntity(EntityManager em) {
        QnOption qnOption = new QnOption()
            .option(UPDATED_OPTION);
        return qnOption;
    }

    @BeforeEach
    public void initTest() {
        qnOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createQnOption() throws Exception {
        int databaseSizeBeforeCreate = qnOptionRepository.findAll().size();
        // Create the QnOption
        QnOptionDTO qnOptionDTO = qnOptionMapper.toDto(qnOption);
        restQnOptionMockMvc.perform(post("/api/qn-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qnOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the QnOption in the database
        List<QnOption> qnOptionList = qnOptionRepository.findAll();
        assertThat(qnOptionList).hasSize(databaseSizeBeforeCreate + 1);
        QnOption testQnOption = qnOptionList.get(qnOptionList.size() - 1);
        assertThat(testQnOption.getOption()).isEqualTo(DEFAULT_OPTION);
    }

    @Test
    @Transactional
    public void createQnOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qnOptionRepository.findAll().size();

        // Create the QnOption with an existing ID
        qnOption.setId(1L);
        QnOptionDTO qnOptionDTO = qnOptionMapper.toDto(qnOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQnOptionMockMvc.perform(post("/api/qn-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qnOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QnOption in the database
        List<QnOption> qnOptionList = qnOptionRepository.findAll();
        assertThat(qnOptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQnOptions() throws Exception {
        // Initialize the database
        qnOptionRepository.saveAndFlush(qnOption);

        // Get all the qnOptionList
        restQnOptionMockMvc.perform(get("/api/qn-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qnOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].option").value(hasItem(DEFAULT_OPTION)));
    }
    
    @Test
    @Transactional
    public void getQnOption() throws Exception {
        // Initialize the database
        qnOptionRepository.saveAndFlush(qnOption);

        // Get the qnOption
        restQnOptionMockMvc.perform(get("/api/qn-options/{id}", qnOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qnOption.getId().intValue()))
            .andExpect(jsonPath("$.option").value(DEFAULT_OPTION));
    }
    @Test
    @Transactional
    public void getNonExistingQnOption() throws Exception {
        // Get the qnOption
        restQnOptionMockMvc.perform(get("/api/qn-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQnOption() throws Exception {
        // Initialize the database
        qnOptionRepository.saveAndFlush(qnOption);

        int databaseSizeBeforeUpdate = qnOptionRepository.findAll().size();

        // Update the qnOption
        QnOption updatedQnOption = qnOptionRepository.findById(qnOption.getId()).get();
        // Disconnect from session so that the updates on updatedQnOption are not directly saved in db
        em.detach(updatedQnOption);
        updatedQnOption
            .option(UPDATED_OPTION);
        QnOptionDTO qnOptionDTO = qnOptionMapper.toDto(updatedQnOption);

        restQnOptionMockMvc.perform(put("/api/qn-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qnOptionDTO)))
            .andExpect(status().isOk());

        // Validate the QnOption in the database
        List<QnOption> qnOptionList = qnOptionRepository.findAll();
        assertThat(qnOptionList).hasSize(databaseSizeBeforeUpdate);
        QnOption testQnOption = qnOptionList.get(qnOptionList.size() - 1);
        assertThat(testQnOption.getOption()).isEqualTo(UPDATED_OPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingQnOption() throws Exception {
        int databaseSizeBeforeUpdate = qnOptionRepository.findAll().size();

        // Create the QnOption
        QnOptionDTO qnOptionDTO = qnOptionMapper.toDto(qnOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQnOptionMockMvc.perform(put("/api/qn-options").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qnOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QnOption in the database
        List<QnOption> qnOptionList = qnOptionRepository.findAll();
        assertThat(qnOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQnOption() throws Exception {
        // Initialize the database
        qnOptionRepository.saveAndFlush(qnOption);

        int databaseSizeBeforeDelete = qnOptionRepository.findAll().size();

        // Delete the qnOption
        restQnOptionMockMvc.perform(delete("/api/qn-options/{id}", qnOption.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QnOption> qnOptionList = qnOptionRepository.findAll();
        assertThat(qnOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
