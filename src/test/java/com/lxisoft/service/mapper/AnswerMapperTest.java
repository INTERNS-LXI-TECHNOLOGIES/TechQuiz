package com.lxisoft.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lxisoft.service.mapper.impl.AnswerMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerMapperTest {

    private AnswerMapper answerMapper;

    @BeforeEach
    public void setUp() {
        answerMapper = new AnswerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(answerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(answerMapper.fromId(null)).isNull();
    }
}
