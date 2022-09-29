package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subcontratista} and its DTO {@link SubcontratistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcontratistaMapper extends EntityMapper<SubcontratistaDTO, Subcontratista> {
    @Mapping(target = "obras", source = "obras", qualifiedByName = "obraNameSet")
    SubcontratistaDTO toDto(Subcontratista s);

    @Mapping(target = "removeObra", ignore = true)
    Subcontratista toEntity(SubcontratistaDTO subcontratistaDTO);

    @Named("obraName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ObraDTO toDtoObraName(Obra obra);

    @Named("obraNameSet")
    default Set<ObraDTO> toDtoObraNameSet(Set<Obra> obra) {
        return obra.stream().map(this::toDtoObraName).collect(Collectors.toSet());
    }
}
