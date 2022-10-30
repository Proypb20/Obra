package com.ojeda.obras.service;

import com.ojeda.obras.domain.SubcoPayAmount;
import com.ojeda.obras.repository.SubcoPayAmountRepository;
import com.ojeda.obras.service.dto.SubcoPayAmountDTO;
import com.ojeda.obras.service.mapper.SubcoPayAmountMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubcoPayAmount}.
 */
@Service
@Transactional
public class SubcoPayAmountService {

    private final Logger log = LoggerFactory.getLogger(SubcoPayAmountService.class);

    private final SubcoPayAmountRepository subcoPayAmountRepository;

    private final SubcoPayAmountMapper subcoPayAmountMapper;

    public SubcoPayAmountService(SubcoPayAmountRepository subcoPayAmountRepository, SubcoPayAmountMapper subcoPayAmountMapper) {
        this.subcoPayAmountRepository = subcoPayAmountRepository;
        this.subcoPayAmountMapper = subcoPayAmountMapper;
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubcoPayAmountDTO> findAll() {
        log.debug("Request to get all subcoPayAmount");
        return subcoPayAmountRepository
            .findAll()
            .stream()
            .map(subcoPayAmountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
