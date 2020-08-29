package com.lxisoft.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lxisoft.web.rest.TestUtil;

public class QnOptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QnOption.class);
        QnOption qnOption1 = new QnOption();
        qnOption1.setId(1L);
        QnOption qnOption2 = new QnOption();
        qnOption2.setId(qnOption1.getId());
        assertThat(qnOption1).isEqualTo(qnOption2);
        qnOption2.setId(2L);
        assertThat(qnOption1).isNotEqualTo(qnOption2);
        qnOption1.setId(null);
        assertThat(qnOption1).isNotEqualTo(qnOption2);
    }
}
