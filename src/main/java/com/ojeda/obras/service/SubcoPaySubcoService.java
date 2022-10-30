package com.ojeda.obras.service;

import com.ojeda.obras.domain.SubcoPaySubco;
import com.ojeda.obras.repository.SubcoPaySubcoRepository;
import com.ojeda.obras.service.dto.SubcoPaySubcoDTO;
import com.ojeda.obras.service.mapper.SubcoPaySubcoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubcoPaySubco}.
 */
@Service
@Transactional
public class SubcoPaySubcoService {

    private final Logger log = LoggerFactory.getLogger(SubcoPaySubcoService.class);

    private final SubcoPaySubcoRepository subcoPaySubcoRepository;

    private final SubcoPaySubcoMapper subcoPaySubcoMapper;

    public SubcoPaySubcoService(SubcoPaySubcoRepository subcoPaySubcoRepository, SubcoPaySubcoMapper subcoPaySubcoMapper) {
        this.subcoPaySubcoRepository = subcoPaySubcoRepository;
        this.subcoPaySubcoMapper = subcoPaySubcoMapper;
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubcoPaySubcoDTO> findAll() {
        log.debug("Request to get all subcoPaySubco");
        return subcoPaySubcoRepository.findAll().stream().map(subcoPaySubcoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<SubcoPaySubcoDTO> findByObraId(Long ObraId) {
        log.debug("Request to get all subcoPaySubco");
        return subcoPaySubcoRepository
            .findByObraId(ObraId)
            .stream()
            .map(subcoPaySubcoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
