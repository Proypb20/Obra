package com.ojeda.obras.service;

import com.ojeda.obras.domain.SubcoPayConcept;
import com.ojeda.obras.repository.SubcoPayConceptRepository;
import com.ojeda.obras.repository.SubcoPayConceptRepository;
import com.ojeda.obras.service.dto.SubcoPayConceptDTO;
import com.ojeda.obras.service.mapper.SubcoPayConceptMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubcoPayConcept}.
 */
@Service
@Transactional
public class SubcoPayConceptService {

    private final Logger log = LoggerFactory.getLogger(SubcoPayConceptService.class);

    private final SubcoPayConceptRepository subcoPayConceptRepository;

    private final SubcoPayConceptMapper subcoPayConceptMapper;

    public SubcoPayConceptService(SubcoPayConceptRepository subcoPayConceptRepository, SubcoPayConceptMapper subcoPayConceptMapper) {
        this.subcoPayConceptRepository = subcoPayConceptRepository;
        this.subcoPayConceptMapper = subcoPayConceptMapper;
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubcoPayConceptDTO> findAll() {
        log.debug("Request to get all subcoPayConcepts");
        return subcoPayConceptRepository
            .findAll()
            .stream()
            .map(subcoPayConceptMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
