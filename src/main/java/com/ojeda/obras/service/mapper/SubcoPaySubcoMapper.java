package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.SubcoPaySubco;
import com.ojeda.obras.service.dto.SubcoPaySubcoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SubcoPaySubco} and its DTO {@link SubcoPaySubcoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcoPaySubcoMapper extends EntityMapper<SubcoPaySubcoDTO, SubcoPaySubco> {}
