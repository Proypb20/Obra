package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleAcopio} and its DTO {@link DetalleAcopioDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalleAcopioMapper extends EntityMapper<DetalleAcopioDTO, DetalleAcopio> {
    @Mapping(target = "acopio", source = "acopio", qualifiedByName = "acopioId")
    DetalleAcopioDTO toDto(DetalleAcopio s);

    @Named("acopioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AcopioDTO toDtoAcopioId(Acopio acopio);
}
