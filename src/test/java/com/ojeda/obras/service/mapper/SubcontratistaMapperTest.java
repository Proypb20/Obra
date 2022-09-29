package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubcontratistaMapperTest {

    private SubcontratistaMapper subcontratistaMapper;

    @BeforeEach
    public void setUp() {
        subcontratistaMapper = new SubcontratistaMapperImpl();
    }
}
