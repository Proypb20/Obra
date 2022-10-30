package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.SubcoPayAmount;
import com.ojeda.obras.service.dto.SubcoPayAmountDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SubcoPayAmount} and its DTO {@link SubcoPayAmountDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcoPayAmountMapper extends EntityMapper<SubcoPayAmountDTO, SubcoPayAmount> {}
