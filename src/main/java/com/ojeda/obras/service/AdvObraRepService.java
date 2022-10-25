package com.ojeda.obras.service;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.repository.AdvObraRepRepository;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import com.ojeda.obras.service.mapper.AdvObraRepMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdvObraRep}.
 */
@Service
@Transactional
public class AdvObraRepService {

    private final Logger log = LoggerFactory.getLogger(AdvObraRepService.class);

    private final AdvObraRepRepository advObraRepRepository;

    private final AdvObraRepMapper advObraRepMapper;

    public AdvObraRepService(AdvObraRepRepository advObraRepRepository, AdvObraRepMapper advObraRepMapper) {
        this.advObraRepRepository = advObraRepRepository;
        this.advObraRepMapper = advObraRepMapper;
    }

    /**
     * Get all the conceptos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvObraRepDTO> findAll() {
        log.debug("Request to get all advObraReps");
        return advObraRepRepository.findAll().stream().map(advObraRepMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
