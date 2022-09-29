package com.ojeda.obras.service;

import com.ojeda.obras.domain.Transaccion;
import com.ojeda.obras.repository.TransaccionRepository;
import com.ojeda.obras.service.dto.TransaccionDTO;
import com.ojeda.obras.service.mapper.TransaccionMapper;
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
 * Service Implementation for managing {@link Transaccion}.
 */
@Service
@Transactional
public class TransaccionService {

    private final Logger log = LoggerFactory.getLogger(TransaccionService.class);

    private final TransaccionRepository transaccionRepository;

    private final TransaccionMapper transaccionMapper;

    public TransaccionService(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
    }

    /**
     * Save a transaccion.
     *
     * @param transaccionDTO the entity to save.
     * @return the persisted entity.
     */
    public TransaccionDTO save(TransaccionDTO transaccionDTO) {
        log.debug("Request to save Transaccion : {}", transaccionDTO);
        Transaccion transaccion = transaccionMapper.toEntity(transaccionDTO);
        transaccion = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(transaccion);
    }

    /**
     * Update a transaccion.
     *
     * @param transaccionDTO the entity to save.
     * @return the persisted entity.
     */
    public TransaccionDTO update(TransaccionDTO transaccionDTO) {
        log.debug("Request to update Transaccion : {}", transaccionDTO);
        Transaccion transaccion = transaccionMapper.toEntity(transaccionDTO);
        transaccion = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(transaccion);
    }

    /**
     * Partially update a transaccion.
     *
     * @param transaccionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransaccionDTO> partialUpdate(TransaccionDTO transaccionDTO) {
        log.debug("Request to partially update Transaccion : {}", transaccionDTO);

        return transaccionRepository
            .findById(transaccionDTO.getId())
            .map(existingTransaccion -> {
                transaccionMapper.partialUpdate(existingTransaccion, transaccionDTO);

                return existingTransaccion;
            })
            .map(transaccionRepository::save)
            .map(transaccionMapper::toDto);
    }

    /**
     * Get all the transaccions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TransaccionDTO> findAll() {
        log.debug("Request to get all Transaccions");
        return transaccionRepository.findAll().stream().map(transaccionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the transaccions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TransaccionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transaccionRepository.findAllWithEagerRelationships(pageable).map(transaccionMapper::toDto);
    }

    /**
     * Get one transaccion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransaccionDTO> findOne(Long id) {
        log.debug("Request to get Transaccion : {}", id);
        return transaccionRepository.findOneWithEagerRelationships(id).map(transaccionMapper::toDto);
    }

    /**
     * Delete the transaccion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transaccion : {}", id);
        transaccionRepository.deleteById(id);
    }
}
