package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovimientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movimiento.class);
        Movimiento movimiento1 = new Movimiento();
        movimiento1.setId(1L);
        Movimiento movimiento2 = new Movimiento();
        movimiento2.setId(movimiento1.getId());
        assertThat(movimiento1).isEqualTo(movimiento2);
        movimiento2.setId(2L);
        assertThat(movimiento1).isNotEqualTo(movimiento2);
        movimiento1.setId(null);
        assertThat(movimiento1).isNotEqualTo(movimiento2);
    }
}
