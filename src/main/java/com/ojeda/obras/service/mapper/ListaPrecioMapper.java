package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import com.ojeda.obras.service.dto.ProveedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListaPrecio} and its DTO {@link ListaPrecioDTO}.
 */
@Mapper(componentModel = "spring")
public interface ListaPrecioMapper extends EntityMapper<ListaPrecioDTO, ListaPrecio> {
    @Mapping(target = "proveedor", source = "proveedor", qualifiedByName = "proveedorName")
    ListaPrecioDTO toDto(ListaPrecio s);

    @Named("proveedorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProveedorDTO toDtoProveedorName(Proveedor proveedor);
}
