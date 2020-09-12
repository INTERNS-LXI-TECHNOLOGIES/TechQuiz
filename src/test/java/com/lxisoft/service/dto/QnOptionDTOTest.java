package com.lxisoft.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lxisoft.web.rest.TestUtil;

public class QnOptionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QnOptionDTO.class);
        QnOptionDTO qnOptionDTO1 = new QnOptionDTO();
        qnOptionDTO1.setId(1L);
        QnOptionDTO qnOptionDTO2 = new QnOptionDTO();
        assertThat(qnOptionDTO1).isNotEqualTo(qnOptionDTO2);
        qnOptionDTO2.setId(qnOptionDTO1.getId());
        assertThat(qnOptionDTO1).isEqualTo(qnOptionDTO2);
        qnOptionDTO2.setId(2L);
        assertThat(qnOptionDTO1).isNotEqualTo(qnOptionDTO2);
        qnOptionDTO1.setId(null);
        assertThat(qnOptionDTO1).isNotEqualTo(qnOptionDTO2);
    }
}
