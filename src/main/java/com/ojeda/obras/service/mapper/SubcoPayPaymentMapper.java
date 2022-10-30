package com.ojeda.obras.service.mapper;

import com.ojeda.obras.domain.SubcoPayPayment;
import com.ojeda.obras.service.dto.SubcoPayPaymentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SubcoPayPayment} and its DTO {@link SubcoPayPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcoPayPaymentMapper extends EntityMapper<SubcoPayPaymentDTO, SubcoPayPayment> {}
