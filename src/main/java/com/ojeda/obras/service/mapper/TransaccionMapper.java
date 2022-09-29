package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.*;
import com.ojeda.obras.service.dto.*;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaccion} and its DTO {@link TransaccionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransaccionMapper extends EntityMapper<TransaccionDTO, Transaccion> {
    @Mapping(target = "obra", source = "obra", qualifiedByName = "obraName")
    @Mapping(target = "subcontratista", source = "subcontratista", qualifiedByName = "subcontratistaLastName")
    @Mapping(target = "tipoComprobante", source = "tipoComprobante", qualifiedByName = "tipoComprobanteName")
    @Mapping(target = "concepto", source = "concepto", qualifiedByName = "conceptoName")
    TransaccionDTO toDto(Transaccion s);

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

    @Named("tipoComprobanteName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "sign", source = "sign")
    TipoComprobanteDTO toDtoTipoComprobanteName(TipoComprobante tipoComprobante);

    @Named("conceptoName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ConceptoDTO toDtoConceptoName(Concepto concepto);
}
