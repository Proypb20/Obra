package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleAcopioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleAcopio.class);
        DetalleAcopio detalleAcopio1 = new DetalleAcopio();
        detalleAcopio1.setId(1L);
        DetalleAcopio detalleAcopio2 = new DetalleAcopio();
        detalleAcopio2.setId(detalleAcopio1.getId());
        assertThat(detalleAcopio1).isEqualTo(detalleAcopio2);
        detalleAcopio2.setId(2L);
        assertThat(detalleAcopio1).isNotEqualTo(detalleAcopio2);
        detalleAcopio1.setId(null);
        assertThat(detalleAcopio1).isNotEqualTo(detalleAcopio2);
    }
}
