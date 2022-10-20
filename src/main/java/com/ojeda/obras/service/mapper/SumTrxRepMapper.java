package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.SumTrxRep;
import com.ojeda.obras.service.dto.SumTrxRepDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SumTrxRep} and its DTO {@link SumTrxRepDTO}.
 */
@Mapper(componentModel = "spring")
public interface SumTrxRepMapper extends EntityMapper<SumTrxRepDTO, SumTrxRep> {}
