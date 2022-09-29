package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransaccionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaccion.class);
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setId(1L);
        Transaccion transaccion2 = new Transaccion();
        transaccion2.setId(transaccion1.getId());
        assertThat(transaccion1).isEqualTo(transaccion2);
        transaccion2.setId(2L);
        assertThat(transaccion1).isNotEqualTo(transaccion2);
        transaccion1.setId(null);
        assertThat(transaccion1).isNotEqualTo(transaccion2);
    }
}
