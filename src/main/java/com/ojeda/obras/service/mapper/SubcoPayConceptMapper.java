package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.SubcoPayConcept;
import com.ojeda.obras.service.dto.SubcoPayConceptDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SubcoPayConcept} and its DTO {@link SubcoPayConceptDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcoPayConceptMapper extends EntityMapper<SubcoPayConceptDTO, SubcoPayConcept> {}
