package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Cliente;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.service.dto.ClienteDTO;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "provinciaName")
    ClienteDTO toDto(Cliente s);

    @Named("provinciaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinciaDTO toDtoProvinciaName(Provincia provincia);
}
