package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Seguimiento;
import com.ojeda.obras.service.dto.SeguimientoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Seguimiento} and its DTO {@link SeguimientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SeguimientoMapper extends EntityMapper<SeguimientoDTO, Seguimiento> {}
