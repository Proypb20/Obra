package com.ojeda.obras.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransaccionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransaccionDTO.class);
        TransaccionDTO transaccionDTO1 = new TransaccionDTO();
        transaccionDTO1.setId(1L);
        TransaccionDTO transaccionDTO2 = new TransaccionDTO();
        assertThat(transaccionDTO1).isNotEqualTo(transaccionDTO2);
        transaccionDTO2.setId(transaccionDTO1.getId());
        assertThat(transaccionDTO1).isEqualTo(transaccionDTO2);
        transaccionDTO2.setId(2L);
        assertThat(transaccionDTO1).isNotEqualTo(transaccionDTO2);
        transaccionDTO1.setId(null);
        assertThat(transaccionDTO1).isNotEqualTo(transaccionDTO2);
    }
}
