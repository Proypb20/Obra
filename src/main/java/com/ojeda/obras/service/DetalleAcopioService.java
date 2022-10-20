package com.ojeda.obras.service;

import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.repository.DetalleAcopioRepository;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.service.mapper.DetalleAcopioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetalleAcopio}.
 */
@Service
@Transactional
public class DetalleAcopioService {

    private final Logger log = LoggerFactory.getLogger(DetalleAcopioService.class);

    private final DetalleAcopioRepository detalleAcopioRepository;

    private final DetalleAcopioMapper detalleAcopioMapper;

    public DetalleAcopioService(DetalleAcopioRepository detalleAcopioRepository, DetalleAcopioMapper detalleAcopioMapper) {
        this.detalleAcopioRepository = detalleAcopioRepository;
        this.detalleAcopioMapper = detalleAcopioMapper;
    }

    /**
     * Save a detalleAcopio.
     *
     * @param detalleAcopioDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleAcopioDTO save(DetalleAcopioDTO detalleAcopioDTO) {
        log.debug("Request to save DetalleAcopio : {}", detalleAcopioDTO);
        DetalleAcopio detalleAcopio = detalleAcopioMapper.toEntity(detalleAcopioDTO);
        detalleAcopio = detalleAcopioRepository.save(detalleAcopio);
        return detalleAcopioMapper.toDto(detalleAcopio);
    }

    /**
     * Update a detalleAcopio.
     *
     * @param detalleAcopioDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleAcopioDTO update(DetalleAcopioDTO detalleAcopioDTO) {
        log.debug("Request to update DetalleAcopio : {}", detalleAcopioDTO);
        DetalleAcopio detalleAcopio = detalleAcopioMapper.toEntity(detalleAcopioDTO);
        detalleAcopio = detalleAcopioRepository.save(detalleAcopio);
        return detalleAcopioMapper.toDto(detalleAcopio);
    }

    /**
     * Partially update a detalleAcopio.
     *
     * @param detalleAcopioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetalleAcopioDTO> partialUpdate(DetalleAcopioDTO detalleAcopioDTO) {
        log.debug("Request to partially update DetalleAcopio : {}", detalleAcopioDTO);

        return detalleAcopioRepository
            .findById(detalleAcopioDTO.getId())
            .map(existingDetalleAcopio -> {
                detalleAcopioMapper.partialUpdate(existingDetalleAcopio, detalleAcopioDTO);

                return existingDetalleAcopio;
            })
            .map(detalleAcopioRepository::save)
            .map(detalleAcopioMapper::toDto);
    }

    /**
     * Get all the detalleAcopios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DetalleAcopioDTO> findAll() {
        log.debug("Request to get all DetalleAcopios");
        return detalleAcopioRepository.findAll().stream().map(detalleAcopioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the detalleAcopios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DetalleAcopioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detalleAcopioRepository.findAllWithEagerRelationships(pageable).map(detalleAcopioMapper::toDto);
    }

    /**
     * Get one detalleAcopio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalleAcopioDTO> findOne(Long id) {
        log.debug("Request to get DetalleAcopio : {}", id);
        return detalleAcopioRepository.findOneWithEagerRelationships(id).map(detalleAcopioMapper::toDto);
    }

    /**
     * Delete the detalleAcopio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleAcopio : {}", id);
        detalleAcopioRepository.deleteById(id);
    }
}
