package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TareaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TareaDTO.class);
        TareaDTO tareaDTO1 = new TareaDTO();
        tareaDTO1.setId(1L);
        TareaDTO tareaDTO2 = new TareaDTO();
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO2.setId(tareaDTO1.getId());
        assertThat(tareaDTO1).isEqualTo(tareaDTO2);
        tareaDTO2.setId(2L);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO1.setId(null);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
    }
}
