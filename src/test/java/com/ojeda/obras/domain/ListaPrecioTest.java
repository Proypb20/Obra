package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListaPrecioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPrecio.class);
        ListaPrecio listaPrecio1 = new ListaPrecio();
        listaPrecio1.setId(1L);
        ListaPrecio listaPrecio2 = new ListaPrecio();
        listaPrecio2.setId(listaPrecio1.getId());
        assertThat(listaPrecio1).isEqualTo(listaPrecio2);
        listaPrecio2.setId(2L);
        assertThat(listaPrecio1).isNotEqualTo(listaPrecio2);
        listaPrecio1.setId(null);
        assertThat(listaPrecio1).isNotEqualTo(listaPrecio2);
    }
}
