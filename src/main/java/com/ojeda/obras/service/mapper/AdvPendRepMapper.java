package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.AdvPendRep;
import com.ojeda.obras.service.dto.AdvPendRepDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AdvPendRep} and its DTO {@link AdvPendRepDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdvPendRepMapper extends EntityMapper<AdvPendRepDTO, AdvPendRep> {}
