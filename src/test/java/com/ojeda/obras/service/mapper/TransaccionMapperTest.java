package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransaccionMapperTest {

    private TransaccionMapper transaccionMapper;

    @BeforeEach
    public void setUp() {
        transaccionMapper = new TransaccionMapperImpl();
    }
}
