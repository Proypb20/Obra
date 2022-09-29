package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadMedidaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedidaDTO.class);
        UnidadMedidaDTO unidadMedidaDTO1 = new UnidadMedidaDTO();
        unidadMedidaDTO1.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO2 = new UnidadMedidaDTO();
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(unidadMedidaDTO1.getId());
        assertThat(unidadMedidaDTO1).isEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(2L);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO1.setId(null);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
    }
}
