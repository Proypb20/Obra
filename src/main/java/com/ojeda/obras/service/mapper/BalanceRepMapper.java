package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.BalanceRep;
import com.ojeda.obras.service.dto.BalanceRepDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link BalanceRep} and its DTO {@link BalanceRepDTO}.
 */
@Mapper(componentModel = "spring")
public interface BalanceRepMapper extends EntityMapper<BalanceRepDTO, BalanceRep> {}
