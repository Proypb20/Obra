package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleAcopio} and its DTO {@link DetalleAcopioDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalleAcopioMapper extends EntityMapper<DetalleAcopioDTO, DetalleAcopio> {
    @Mapping(target = "acopio", source = "acopio", qualifiedByName = "acopioId")
    @Mapping(target = "detalleListaPrecio", source = "detalleListaPrecio", qualifiedByName = "detalleListaPrecioProduct")
    DetalleAcopioDTO toDto(DetalleAcopio s);

    @Named("acopioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AcopioDTO toDtoAcopioId(Acopio acopio);

    @Named("detalleListaPrecioProduct")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    DetalleListaPrecioDTO toDtoDetalleListaPrecioProduct(DetalleListaPrecio detalleListaPrecio);
}
