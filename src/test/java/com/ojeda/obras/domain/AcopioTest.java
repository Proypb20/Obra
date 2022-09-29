package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcopioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acopio.class);
        Acopio acopio1 = new Acopio();
        acopio1.setId(1L);
        Acopio acopio2 = new Acopio();
        acopio2.setId(acopio1.getId());
        assertThat(acopio1).isEqualTo(acopio2);
        acopio2.setId(2L);
        assertThat(acopio1).isNotEqualTo(acopio2);
        acopio1.setId(null);
        assertThat(acopio1).isNotEqualTo(acopio2);
    }
}
