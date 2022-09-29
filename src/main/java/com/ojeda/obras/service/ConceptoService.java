package com.ojeda.obras.service;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.repository.ConceptoRepository;
import com.ojeda.obras.service.dto.ConceptoDTO;
import com.ojeda.obras.service.mapper.ConceptoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Concepto}.
 */
@Service
@Transactional
public class ConceptoService {

    private final Logger log = LoggerFactory.getLogger(ConceptoService.class);

    private final ConceptoRepository conceptoRepository;

    private final ConceptoMapper conceptoMapper;

    public ConceptoService(ConceptoRepository conceptoRepository, ConceptoMapper conceptoMapper) {
        this.conceptoRepository = conceptoRepository;
        this.conceptoMapper = conceptoMapper;
    }

    /**
     * Save a concepto.
     *
     * @param conceptoDTO the entity to save.
     * @return the persisted entity.
     */
    public ConceptoDTO save(ConceptoDTO conceptoDTO) {
        log.debug("Request to save Concepto : {}", conceptoDTO);
        Concepto concepto = conceptoMapper.toEntity(conceptoDTO);
        concepto = conceptoRepository.save(concepto);
        return conceptoMapper.toDto(concepto);
    }

    /**
     * Update a concepto.
     *
     * @param conceptoDTO the entity to save.
     * @return the persisted entity.
     */
    public ConceptoDTO update(ConceptoDTO conceptoDTO) {
        log.debug("Request to update Concepto : {}", conceptoDTO);
        Concepto concepto = conceptoMapper.toEntity(conceptoDTO);
        concepto = conceptoRepository.save(concepto);
        return conceptoMapper.toDto(concepto);
    }

    /**
     * Partially update a concepto.
     *
     * @param conceptoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConceptoDTO> partialUpdate(ConceptoDTO conceptoDTO) {
        log.debug("Request to partially update Concepto : {}", conceptoDTO);

        return conceptoRepository
            .findById(conceptoDTO.getId())
            .map(existingConcepto -> {
                conceptoMapper.partialUpdate(existingConcepto, conceptoDTO);

                return existingConcepto;
            })
            .map(conceptoRepository::save)
            .map(conceptoMapper::toDto);
    }

    /**
     * Get all the conceptos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConceptoDTO> findAll() {
        log.debug("Request to get all Conceptos");
        return conceptoRepository.findAll().stream().map(conceptoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one concepto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConceptoDTO> findOne(Long id) {
        log.debug("Request to get Concepto : {}", id);
        return conceptoRepository.findById(id).map(conceptoMapper::toDto);
    }

    /**
     * Delete the concepto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Concepto : {}", id);
        conceptoRepository.deleteById(id);
    }
}
