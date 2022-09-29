package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubcontratistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubcontratistaDTO.class);
        SubcontratistaDTO subcontratistaDTO1 = new SubcontratistaDTO();
        subcontratistaDTO1.setId(1L);
        SubcontratistaDTO subcontratistaDTO2 = new SubcontratistaDTO();
        assertThat(subcontratistaDTO1).isNotEqualTo(subcontratistaDTO2);
        subcontratistaDTO2.setId(subcontratistaDTO1.getId());
        assertThat(subcontratistaDTO1).isEqualTo(subcontratistaDTO2);
        subcontratistaDTO2.setId(2L);
        assertThat(subcontratistaDTO1).isNotEqualTo(subcontratistaDTO2);
        subcontratistaDTO1.setId(null);
        assertThat(subcontratistaDTO1).isNotEqualTo(subcontratistaDTO2);
    }
}
