package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovimientoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoDTO.class);
        MovimientoDTO movimientoDTO1 = new MovimientoDTO();
        movimientoDTO1.setId(1L);
        MovimientoDTO movimientoDTO2 = new MovimientoDTO();
        assertThat(movimientoDTO1).isNotEqualTo(movimientoDTO2);
        movimientoDTO2.setId(movimientoDTO1.getId());
        assertThat(movimientoDTO1).isEqualTo(movimientoDTO2);
        movimientoDTO2.setId(2L);
        assertThat(movimientoDTO1).isNotEqualTo(movimientoDTO2);
        movimientoDTO1.setId(null);
        assertThat(movimientoDTO1).isNotEqualTo(movimientoDTO2);
    }
}
