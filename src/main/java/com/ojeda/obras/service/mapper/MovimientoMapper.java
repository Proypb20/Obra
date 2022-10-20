package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Movimiento;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.service.dto.MovimientoDTO;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movimiento} and its DTO {@link MovimientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MovimientoMapper extends EntityMapper<MovimientoDTO, Movimiento> {
    @Mapping(target = "obra", source = "obra", qualifiedByName = "obraName")
    @Mapping(target = "subcontratista", source = "subcontratista", qualifiedByName = "subcontratistaLastName")
    MovimientoDTO toDto(Movimiento s);

    @Named("obraName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ObraDTO toDtoObraName(Obra obra);

    @Named("subcontratistaLastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastName", source = "lastName")
    SubcontratistaDTO toDtoSubcontratistaLastName(Subcontratista subcontratista);
}
