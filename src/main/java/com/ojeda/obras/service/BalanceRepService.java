package com.ojeda.obras.service;

import com.ojeda.obras.domain.BalanceRep;
import com.ojeda.obras.repository.BalanceRepRepository;
import com.ojeda.obras.service.dto.BalanceRepDTO;
import com.ojeda.obras.service.mapper.BalanceRepMapper;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BalanceRep}.
 */
@Service
@Transactional
public class BalanceRepService {

    private final Logger log = LoggerFactory.getLogger(BalanceRepService.class);

    private final BalanceRepRepository balanceRepRepository;

    private final BalanceRepMapper balanceRepMapper;

    public BalanceRepService(BalanceRepRepository balanceRepRepository, BalanceRepMapper balanceRepMapper) {
        this.balanceRepRepository = balanceRepRepository;
        this.balanceRepMapper = balanceRepMapper;
    }

    /**
     * Get all the BalanceReps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BalanceRepDTO> findAllBetweenDates(Instant dateFrom, Instant dateTo) {
        log.debug("Request to get all balanceReps");
        return balanceRepRepository.findAll().stream().map(balanceRepMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
