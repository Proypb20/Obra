package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleListaPrecio} and its DTO {@link DetalleListaPrecioDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalleListaPrecioMapper extends EntityMapper<DetalleListaPrecioDTO, DetalleListaPrecio> {
    @Mapping(target = "listaPrecio", source = "listaPrecio", qualifiedByName = "listaPrecioName")
    DetalleListaPrecioDTO toDto(DetalleListaPrecio s);

    @Named("listaPrecioName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ListaPrecioDTO toDtoListaPrecioName(ListaPrecio listaPrecio);
}
