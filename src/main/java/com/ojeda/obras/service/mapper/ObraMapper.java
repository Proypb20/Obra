package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Cliente;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.service.dto.ClienteDTO;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Obra} and its DTO {@link ObraDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObraMapper extends EntityMapper<ObraDTO, Obra> {
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "provinciaName")
    @Mapping(target = "subcontratistas", source = "subcontratistas", qualifiedByName = "subcontratistaIdSet")
    @Mapping(target = "clientes", source = "clientes", qualifiedByName = "clienteIdSet")
    ObraDTO toDto(Obra s);

    @Mapping(target = "removeSubcontratista", ignore = true)
    @Mapping(target = "removeCliente", ignore = true)
    Obra toEntity(ObraDTO obraDTO);

    @Named("provinciaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinciaDTO toDtoProvinciaName(Provincia provincia);

    @Named("subcontratistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubcontratistaDTO toDtoSubcontratistaId(Subcontratista subcontratista);

    @Named("subcontratistaIdSet")
    default Set<SubcontratistaDTO> toDtoSubcontratistaIdSet(Set<Subcontratista> subcontratista) {
        return subcontratista.stream().map(this::toDtoSubcontratistaId).collect(Collectors.toSet());
    }

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);

    @Named("clienteIdSet")
    default Set<ClienteDTO> toDtoClienteIdSet(Set<Cliente> cliente) {
        return cliente.stream().map(this::toDtoClienteId).collect(Collectors.toSet());
    }
}
