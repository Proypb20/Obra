package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.ResObra;
import com.ojeda.obras.service.dto.ResObraDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ResObra} and its DTO {@link ResObraDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResObraMapper extends EntityMapper<ResObraDTO, ResObra> {}
