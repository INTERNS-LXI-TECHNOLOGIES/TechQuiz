package com.lxisoft.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class QnOptionMapperTest {

    private QnOptionMapper qnOptionMapper;

    @BeforeEach
    public void setUp() {
        qnOptionMapper = new QnOptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(qnOptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(qnOptionMapper.fromId(null)).isNull();
    }
}
