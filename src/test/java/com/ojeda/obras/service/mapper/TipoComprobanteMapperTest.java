package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoComprobanteMapperTest {

    private TipoComprobanteMapper tipoComprobanteMapper;

    @BeforeEach
    public void setUp() {
        tipoComprobanteMapper = new TipoComprobanteMapperImpl();
    }
}
