package com.lxisoft.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lxisoft.service.mapper.impl.AttendedExamMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendedExamMapperTest {

    private AttendedExamMapper attendedExamMapper;

    @BeforeEach
    public void setUp() {
        attendedExamMapper = new AttendedExamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attendedExamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attendedExamMapper.fromId(null)).isNull();
    }
}
