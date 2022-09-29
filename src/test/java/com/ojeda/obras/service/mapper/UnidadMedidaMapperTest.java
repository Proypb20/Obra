package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnidadMedidaMapperTest {

    private UnidadMedidaMapper unidadMedidaMapper;

    @BeforeEach
    public void setUp() {
        unidadMedidaMapper = new UnidadMedidaMapperImpl();
    }
}
