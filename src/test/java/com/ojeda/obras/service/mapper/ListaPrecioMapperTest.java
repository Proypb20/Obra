package com.ojeda.obras.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListaPrecioMapperTest {

    private ListaPrecioMapper listaPrecioMapper;

    @BeforeEach
    public void setUp() {
        listaPrecioMapper = new ListaPrecioMapperImpl();
    }
}
