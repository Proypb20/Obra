package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.service.dto.TipoComprobanteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoComprobante} and its DTO {@link TipoComprobanteDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoComprobanteMapper extends EntityMapper<TipoComprobanteDTO, TipoComprobante> {}
