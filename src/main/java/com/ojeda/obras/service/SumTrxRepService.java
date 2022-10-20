package com.ojeda.obras.service;

import com.ojeda.obras.domain.SumTrxRep;
import com.ojeda.obras.repository.SumTrxRepRepository;
import com.ojeda.obras.service.dto.SumTrxRepDTO;
import com.ojeda.obras.service.mapper.SumTrxRepMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SumTrxRep}.
 */
@Service
@Transactional
public class SumTrxRepService {

    private final Logger log = LoggerFactory.getLogger(SumTrxRepService.class);

    private final SumTrxRepRepository sumTrxRepRepository;

    private final SumTrxRepMapper sumTrxRepMapper;

    public SumTrxRepService(SumTrxRepRepository sumTrxRepRepository, SumTrxRepMapper sumTrxRepMapper) {
        this.sumTrxRepRepository = sumTrxRepRepository;
        this.sumTrxRepMapper = sumTrxRepMapper;
    }

    /**
     * Get all the sumTrx.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SumTrxRepDTO> findAll() {
        log.debug("Request to get all sumTrxReps");
        return sumTrxRepRepository.findAll().stream().map(sumTrxRepMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
