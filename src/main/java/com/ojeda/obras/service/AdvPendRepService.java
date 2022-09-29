package com.ojeda.obras.service;

import com.ojeda.obras.domain.AdvPendRep;
import com.ojeda.obras.repository.AdvPendRepRepository;
import com.ojeda.obras.service.dto.AdvPendRepDTO;
import com.ojeda.obras.service.mapper.AdvPendRepMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdvPendRep}.
 */
@Service
@Transactional
public class AdvPendRepService {

    private final Logger log = LoggerFactory.getLogger(AdvPendRepService.class);

    private final AdvPendRepRepository advPendRepRepository;

    private final AdvPendRepMapper advPendRepMapper;

    public AdvPendRepService(AdvPendRepRepository advPendRepRepository, AdvPendRepMapper advPendRepMapper) {
        this.advPendRepRepository = advPendRepRepository;
        this.advPendRepMapper = advPendRepMapper;
    }

    /**
     * Get all the conceptos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvPendRepDTO> findAll() {
        log.debug("Request to get all advPendReps");
        return advPendRepRepository.findAll().stream().map(advPendRepMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
