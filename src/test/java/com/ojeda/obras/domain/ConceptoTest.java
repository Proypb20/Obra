package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConceptoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concepto.class);
        Concepto concepto1 = new Concepto();
        concepto1.setId(1L);
        Concepto concepto2 = new Concepto();
        concepto2.setId(concepto1.getId());
        assertThat(concepto1).isEqualTo(concepto2);
        concepto2.setId(2L);
        assertThat(concepto1).isNotEqualTo(concepto2);
        concepto1.setId(null);
        assertThat(concepto1).isNotEqualTo(concepto2);
    }
}
