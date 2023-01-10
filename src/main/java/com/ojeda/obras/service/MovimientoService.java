package com.ojeda.obras.service;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Movimiento;
import com.ojeda.obras.repository.MovimientoRepository;
import com.ojeda.obras.service.dto.MovimientoDTO;
import com.ojeda.obras.service.mapper.MovimientoMapper;
import java.time.Instant;
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
 * Service Implementation for managing {@link Movimiento}.
 */
@Service
@Transactional
public class MovimientoService {

    private final Logger log = LoggerFactory.getLogger(MovimientoService.class);

    private final MovimientoRepository movimientoRepository;

    private final MovimientoMapper movimientoMapper;

    public MovimientoService(MovimientoRepository movimientoRepository, MovimientoMapper movimientoMapper) {
        this.movimientoRepository = movimientoRepository;
        this.movimientoMapper = movimientoMapper;
    }

    /**
     * Save a movimiento.
     *
     * @param movimientoDTO the entity to save.
     * @return the persisted entity.
     */
    public MovimientoDTO save(MovimientoDTO movimientoDTO) {
        log.debug("Request to save Movimiento : {}", movimientoDTO);
        Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
        movimiento = movimientoRepository.save(movimiento);
        return movimientoMapper.toDto(movimiento);
    }

    /**
     * Update a movimiento.
     *
     * @param movimientoDTO the entity to save.
     * @return the persisted entity.
     */
    public MovimientoDTO update(MovimientoDTO movimientoDTO) {
        log.debug("Request to update Movimiento : {}", movimientoDTO);
        Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
        movimiento = movimientoRepository.save(movimiento);
        return movimientoMapper.toDto(movimiento);
    }

    /**
     * Partially update a movimiento.
     *
     * @param movimientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MovimientoDTO> partialUpdate(MovimientoDTO movimientoDTO) {
        log.debug("Request to partially update Movimiento : {}", movimientoDTO);

        return movimientoRepository
            .findById(movimientoDTO.getId())
            .map(existingMovimiento -> {
                movimientoMapper.partialUpdate(existingMovimiento, movimientoDTO);

                return existingMovimiento;
            })
            .map(movimientoRepository::save)
            .map(movimientoMapper::toDto);
    }

    /**
     * Get all the movimientos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MovimientoDTO> findAll() {
        log.debug("Request to get all Movimientos");
        return movimientoRepository.findAll().stream().map(movimientoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the movimientos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Movimiento> findAllByConceptoAndDateBetween(Concepto concepto, Instant fromDate, Instant toDate) {
        log.debug("Request to get all Movimientos");
        return movimientoRepository.findAllByConceptoAndDateBetween(concepto, fromDate, toDate);
    }

    /**
     * Get all the movimientos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MovimientoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return movimientoRepository.findAllWithEagerRelationships(pageable).map(movimientoMapper::toDto);
    }

    /**
     * Get one movimiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MovimientoDTO> findOne(Long id) {
        log.debug("Request to get Movimiento : {}", id);
        return movimientoRepository.findOneWithEagerRelationships(id).map(movimientoMapper::toDto);
    }

    /**
     * Delete the movimiento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movimiento : {}", id);
        movimientoRepository.deleteById(id);
    }
}
