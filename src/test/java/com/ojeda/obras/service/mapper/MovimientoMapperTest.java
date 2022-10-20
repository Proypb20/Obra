package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovimientoMapperTest {

    private MovimientoMapper movimientoMapper;

    @BeforeEach
    public void setUp() {
        movimientoMapper = new MovimientoMapperImpl();
    }
}
