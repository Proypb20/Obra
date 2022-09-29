package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleListaPrecioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleListaPrecio.class);
        DetalleListaPrecio detalleListaPrecio1 = new DetalleListaPrecio();
        detalleListaPrecio1.setId(1L);
        DetalleListaPrecio detalleListaPrecio2 = new DetalleListaPrecio();
        detalleListaPrecio2.setId(detalleListaPrecio1.getId());
        assertThat(detalleListaPrecio1).isEqualTo(detalleListaPrecio2);
        detalleListaPrecio2.setId(2L);
        assertThat(detalleListaPrecio1).isNotEqualTo(detalleListaPrecio2);
        detalleListaPrecio1.setId(null);
        assertThat(detalleListaPrecio1).isNotEqualTo(detalleListaPrecio2);
    }
}
