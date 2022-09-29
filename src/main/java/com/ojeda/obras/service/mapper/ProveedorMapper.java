package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.service.dto.ProveedorDTO;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proveedor} and its DTO {@link ProveedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "provinciaName")
    ProveedorDTO toDto(Proveedor s);

    @Named("provinciaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinciaDTO toDtoProvinciaName(Provincia provincia);
}
