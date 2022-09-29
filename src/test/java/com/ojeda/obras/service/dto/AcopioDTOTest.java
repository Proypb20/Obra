package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcopioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcopioDTO.class);
        AcopioDTO acopioDTO1 = new AcopioDTO();
        acopioDTO1.setId(1L);
        AcopioDTO acopioDTO2 = new AcopioDTO();
        assertThat(acopioDTO1).isNotEqualTo(acopioDTO2);
        acopioDTO2.setId(acopioDTO1.getId());
        assertThat(acopioDTO1).isEqualTo(acopioDTO2);
        acopioDTO2.setId(2L);
        assertThat(acopioDTO1).isNotEqualTo(acopioDTO2);
        acopioDTO1.setId(null);
        assertThat(acopioDTO1).isNotEqualTo(acopioDTO2);
    }
}
