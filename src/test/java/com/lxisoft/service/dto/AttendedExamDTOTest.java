package com.lxisoft.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lxisoft.web.rest.TestUtil;

public class AttendedExamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendedExamDTO.class);
        AttendedExamDTO attendedExamDTO1 = new AttendedExamDTO();
        attendedExamDTO1.setId(1L);
        AttendedExamDTO attendedExamDTO2 = new AttendedExamDTO();
        assertThat(attendedExamDTO1).isNotEqualTo(attendedExamDTO2);
        attendedExamDTO2.setId(attendedExamDTO1.getId());
        assertThat(attendedExamDTO1).isEqualTo(attendedExamDTO2);
        attendedExamDTO2.setId(2L);
        assertThat(attendedExamDTO1).isNotEqualTo(attendedExamDTO2);
        attendedExamDTO1.setId(null);
        assertThat(attendedExamDTO1).isNotEqualTo(attendedExamDTO2);
    }
}
