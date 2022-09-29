package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcopioMapperTest {

    private AcopioMapper acopioMapper;

    @BeforeEach
    public void setUp() {
        acopioMapper = new AcopioMapperImpl();
    }
}
