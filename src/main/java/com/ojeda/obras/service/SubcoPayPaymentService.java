package com.ojeda.obras.service;

import com.ojeda.obras.domain.SubcoPayPayment;
import com.ojeda.obras.repository.SubcoPayPaymentRepository;
import com.ojeda.obras.service.dto.SubcoPayPaymentDTO;
import com.ojeda.obras.service.mapper.SubcoPayPaymentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubcoPayPayment}.
 */
@Service
@Transactional
public class SubcoPayPaymentService {

    private final Logger log = LoggerFactory.getLogger(SubcoPayPaymentService.class);

    private final SubcoPayPaymentRepository subcoPayPaymentRepository;

    private final SubcoPayPaymentMapper subcoPayPaymentMapper;

    public SubcoPayPaymentService(SubcoPayPaymentRepository subcoPayPaymentRepository, SubcoPayPaymentMapper subcoPayPaymentMapper) {
        this.subcoPayPaymentRepository = subcoPayPaymentRepository;
        this.subcoPayPaymentMapper = subcoPayPaymentMapper;
    }

    /**
     * Get all the subcoPayPayment.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubcoPayPaymentDTO> findAll() {
        log.debug("Request to get all subcoPayPayments");
        return subcoPayPaymentRepository
            .findAll()
            .stream()
            .map(subcoPayPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
