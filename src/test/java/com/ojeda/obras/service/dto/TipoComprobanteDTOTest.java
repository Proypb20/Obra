package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoComprobanteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoComprobanteDTO.class);
        TipoComprobanteDTO tipoComprobanteDTO1 = new TipoComprobanteDTO();
        tipoComprobanteDTO1.setId(1L);
        TipoComprobanteDTO tipoComprobanteDTO2 = new TipoComprobanteDTO();
        assertThat(tipoComprobanteDTO1).isNotEqualTo(tipoComprobanteDTO2);
        tipoComprobanteDTO2.setId(tipoComprobanteDTO1.getId());
        assertThat(tipoComprobanteDTO1).isEqualTo(tipoComprobanteDTO2);
        tipoComprobanteDTO2.setId(2L);
        assertThat(tipoComprobanteDTO1).isNotEqualTo(tipoComprobanteDTO2);
        tipoComprobanteDTO1.setId(null);
        assertThat(tipoComprobanteDTO1).isNotEqualTo(tipoComprobanteDTO2);
    }
}
