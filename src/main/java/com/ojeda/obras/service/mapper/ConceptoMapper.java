package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.service.dto.ConceptoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Concepto} and its DTO {@link ConceptoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConceptoMapper extends EntityMapper<ConceptoDTO, Concepto> {}
