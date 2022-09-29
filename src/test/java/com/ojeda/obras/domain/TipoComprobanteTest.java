package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoComprobanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoComprobante.class);
        TipoComprobante tipoComprobante1 = new TipoComprobante();
        tipoComprobante1.setId(1L);
        TipoComprobante tipoComprobante2 = new TipoComprobante();
        tipoComprobante2.setId(tipoComprobante1.getId());
        assertThat(tipoComprobante1).isEqualTo(tipoComprobante2);
        tipoComprobante2.setId(2L);
        assertThat(tipoComprobante1).isNotEqualTo(tipoComprobante2);
        tipoComprobante1.setId(null);
        assertThat(tipoComprobante1).isNotEqualTo(tipoComprobante2);
    }
}
