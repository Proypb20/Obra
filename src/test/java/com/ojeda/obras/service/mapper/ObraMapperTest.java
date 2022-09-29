package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObraMapperTest {

    private ObraMapper obraMapper;

    @BeforeEach
    public void setUp() {
        obraMapper = new ObraMapperImpl();
    }
}
