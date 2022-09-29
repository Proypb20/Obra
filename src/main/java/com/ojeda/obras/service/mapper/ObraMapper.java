package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Obra} and its DTO {@link ObraDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObraMapper extends EntityMapper<ObraDTO, Obra> {
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "provinciaName")
    ObraDTO toDto(Obra s);

    @Named("provinciaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinciaDTO toDtoProvinciaName(Provincia provincia);
}
