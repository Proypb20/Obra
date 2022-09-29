package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.UnidadMedida;
import com.ojeda.obras.service.dto.UnidadMedidaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadMedida} and its DTO {@link UnidadMedidaDTO}.
 */
@Mapper(componentModel = "spring")
public interface UnidadMedidaMapper extends EntityMapper<UnidadMedidaDTO, UnidadMedida> {}
