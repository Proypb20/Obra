package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListaPrecioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPrecioDTO.class);
        ListaPrecioDTO listaPrecioDTO1 = new ListaPrecioDTO();
        listaPrecioDTO1.setId(1L);
        ListaPrecioDTO listaPrecioDTO2 = new ListaPrecioDTO();
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
        listaPrecioDTO2.setId(listaPrecioDTO1.getId());
        assertThat(listaPrecioDTO1).isEqualTo(listaPrecioDTO2);
        listaPrecioDTO2.setId(2L);
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
        listaPrecioDTO1.setId(null);
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
    }
}
