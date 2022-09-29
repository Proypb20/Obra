package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetalleAcopioMapperTest {

    private DetalleAcopioMapper detalleAcopioMapper;

    @BeforeEach
    public void setUp() {
        detalleAcopioMapper = new DetalleAcopioMapperImpl();
    }
}
