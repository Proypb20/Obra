package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AdvObraRep} and its DTO {@link AdvObraRepDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdvObraRepMapper extends EntityMapper<AdvObraRepDTO, AdvObraRep> {}
