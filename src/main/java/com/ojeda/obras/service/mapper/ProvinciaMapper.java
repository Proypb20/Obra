package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provincia} and its DTO {@link ProvinciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinciaMapper extends EntityMapper<ProvinciaDTO, Provincia> {}
