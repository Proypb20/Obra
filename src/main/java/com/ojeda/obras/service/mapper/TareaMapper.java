package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.domain.UnidadMedida;
import com.ojeda.obras.service.dto.ConceptoDTO;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import com.ojeda.obras.service.dto.TareaDTO;
import com.ojeda.obras.service.dto.UnidadMedidaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarea} and its DTO {@link TareaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {
    @Mapping(target = "obra", source = "obra", qualifiedByName = "obraName")
    @Mapping(target = "subcontratista", source = "subcontratista", qualifiedByName = "subcontratistaLastName")
    @Mapping(target = "concepto", source = "concepto", qualifiedByName = "conceptoName")
    TareaDTO toDto(Tarea s);

    @Named("obraName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ObraDTO toDtoObraName(Obra obra);

    @Named("subcontratistaLastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "firstName", source = "firstName")
    SubcontratistaDTO toDtoSubcontratistaLastName(Subcontratista subcontratista);

    @Named("conceptoName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ConceptoDTO toDtoConceptoName(Concepto concepto);
}
