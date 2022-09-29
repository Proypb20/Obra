package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConceptoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptoDTO.class);
        ConceptoDTO conceptoDTO1 = new ConceptoDTO();
        conceptoDTO1.setId(1L);
        ConceptoDTO conceptoDTO2 = new ConceptoDTO();
        assertThat(conceptoDTO1).isNotEqualTo(conceptoDTO2);
        conceptoDTO2.setId(conceptoDTO1.getId());
        assertThat(conceptoDTO1).isEqualTo(conceptoDTO2);
        conceptoDTO2.setId(2L);
        assertThat(conceptoDTO1).isNotEqualTo(conceptoDTO2);
        conceptoDTO1.setId(null);
        assertThat(conceptoDTO1).isNotEqualTo(conceptoDTO2);
    }
}
