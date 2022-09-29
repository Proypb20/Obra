package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetalleListaPrecioMapperTest {

    private DetalleListaPrecioMapper detalleListaPrecioMapper;

    @BeforeEach
    public void setUp() {
        detalleListaPrecioMapper = new DetalleListaPrecioMapperImpl();
    }
}
