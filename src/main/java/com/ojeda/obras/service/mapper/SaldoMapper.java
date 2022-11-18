package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Saldo;
import com.ojeda.obras.service.dto.SaldoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Saldo} and its DTO {@link SaldoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaldoMapper extends EntityMapper<SaldoDTO, Saldo> {}
