package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProvinciaMapperTest {

    private ProvinciaMapper provinciaMapper;

    @BeforeEach
    public void setUp() {
        provinciaMapper = new ProvinciaMapperImpl();
    }
}
