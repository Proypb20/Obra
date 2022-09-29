package com.ojeda.obras.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ojeda.obras.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obra.class);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(obra1.getId());
        assertThat(obra1).isEqualTo(obra2);
        obra2.setId(2L);
        assertThat(obra1).isNotEqualTo(obra2);
        obra1.setId(null);
        assertThat(obra1).isNotEqualTo(obra2);
    }
}
