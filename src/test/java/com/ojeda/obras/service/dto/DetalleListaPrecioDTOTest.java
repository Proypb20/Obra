package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleListaPrecioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleListaPrecioDTO.class);
        DetalleListaPrecioDTO detalleListaPrecioDTO1 = new DetalleListaPrecioDTO();
        detalleListaPrecioDTO1.setId(1L);
        DetalleListaPrecioDTO detalleListaPrecioDTO2 = new DetalleListaPrecioDTO();
        assertThat(detalleListaPrecioDTO1).isNotEqualTo(detalleListaPrecioDTO2);
        detalleListaPrecioDTO2.setId(detalleListaPrecioDTO1.getId());
        assertThat(detalleListaPrecioDTO1).isEqualTo(detalleListaPrecioDTO2);
        detalleListaPrecioDTO2.setId(2L);
        assertThat(detalleListaPrecioDTO1).isNotEqualTo(detalleListaPrecioDTO2);
        detalleListaPrecioDTO1.setId(null);
        assertThat(detalleListaPrecioDTO1).isNotEqualTo(detalleListaPrecioDTO2);
    }
}
