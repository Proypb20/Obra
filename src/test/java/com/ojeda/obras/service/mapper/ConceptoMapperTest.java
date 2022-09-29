package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConceptoMapperTest {

    private ConceptoMapper conceptoMapper;

    @BeforeEach
    public void setUp() {
        conceptoMapper = new ConceptoMapperImpl();
    }
}
