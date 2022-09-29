package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleAcopioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleAcopioDTO.class);
        DetalleAcopioDTO detalleAcopioDTO1 = new DetalleAcopioDTO();
        detalleAcopioDTO1.setId(1L);
        DetalleAcopioDTO detalleAcopioDTO2 = new DetalleAcopioDTO();
        assertThat(detalleAcopioDTO1).isNotEqualTo(detalleAcopioDTO2);
        detalleAcopioDTO2.setId(detalleAcopioDTO1.getId());
        assertThat(detalleAcopioDTO1).isEqualTo(detalleAcopioDTO2);
        detalleAcopioDTO2.setId(2L);
        assertThat(detalleAcopioDTO1).isNotEqualTo(detalleAcopioDTO2);
        detalleAcopioDTO1.setId(null);
        assertThat(detalleAcopioDTO1).isNotEqualTo(detalleAcopioDTO2);
    }
}
