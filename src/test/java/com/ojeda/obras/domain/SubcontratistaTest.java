package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubcontratistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subcontratista.class);
        Subcontratista subcontratista1 = new Subcontratista();
        subcontratista1.setId(1L);
        Subcontratista subcontratista2 = new Subcontratista();
        subcontratista2.setId(subcontratista1.getId());
        assertThat(subcontratista1).isEqualTo(subcontratista2);
        subcontratista2.setId(2L);
        assertThat(subcontratista1).isNotEqualTo(subcontratista2);
        subcontratista1.setId(null);
        assertThat(subcontratista1).isNotEqualTo(subcontratista2);
    }
}
