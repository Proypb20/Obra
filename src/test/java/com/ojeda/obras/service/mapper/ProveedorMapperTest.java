package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProveedorMapperTest {

    private ProveedorMapper proveedorMapper;

    @BeforeEach
    public void setUp() {
        proveedorMapper = new ProveedorMapperImpl();
    }
}
