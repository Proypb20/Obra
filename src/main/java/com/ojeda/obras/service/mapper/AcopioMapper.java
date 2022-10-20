package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.ProveedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Acopio} and its DTO {@link AcopioDTO}.
 */
@Mapper(componentModel = "spring")
public interface AcopioMapper extends EntityMapper<AcopioDTO, Acopio> {
    @Mapping(target = "obra", source = "obra", qualifiedByName = "obraName")
    @Mapping(target = "listaprecio", source = "listaprecio", qualifiedByName = "listaPrecioName")
    @Mapping(target = "proveedor", source = "proveedor", qualifiedByName = "proveedorName")
    AcopioDTO toDto(Acopio s);

    @Named("obraName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ObraDTO toDtoObraName(Obra obra);

    @Named("listaPrecioName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ListaPrecioDTO toDtoListaPrecioName(ListaPrecio listaPrecio);

    @Named("proveedorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProveedorDTO toDtoProveedorName(Proveedor proveedor);
}
