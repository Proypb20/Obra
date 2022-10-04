package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subcontratista} and its DTO {@link SubcontratistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcontratistaMapper extends EntityMapper<SubcontratistaDTO, Subcontratista> {}
