package com.ojeda.obras.service;

import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.repository.DetalleListaPrecioRepository;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import com.ojeda.obras.service.mapper.DetalleListaPrecioMapper;
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
 * Service Implementation for managing {@link DetalleListaPrecio}.
 */
@Service
@Transactional
public class DetalleListaPrecioService {

    private final Logger log = LoggerFactory.getLogger(DetalleListaPrecioService.class);

    private final DetalleListaPrecioRepository detalleListaPrecioRepository;

    private final DetalleListaPrecioMapper detalleListaPrecioMapper;

    public DetalleListaPrecioService(
        DetalleListaPrecioRepository detalleListaPrecioRepository,
        DetalleListaPrecioMapper detalleListaPrecioMapper
    ) {
        this.detalleListaPrecioRepository = detalleListaPrecioRepository;
        this.detalleListaPrecioMapper = detalleListaPrecioMapper;
    }

    /**
     * Save a detalleListaPrecio.
     *
     * @param detalleListaPrecioDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleListaPrecioDTO save(DetalleListaPrecioDTO detalleListaPrecioDTO) {
        log.debug("Request to save DetalleListaPrecio : {}", detalleListaPrecioDTO);
        DetalleListaPrecio detalleListaPrecio = detalleListaPrecioMapper.toEntity(detalleListaPrecioDTO);
        detalleListaPrecio = detalleListaPrecioRepository.save(detalleListaPrecio);
        return detalleListaPrecioMapper.toDto(detalleListaPrecio);
    }

    /**
     * Update a detalleListaPrecio.
     *
     * @param detalleListaPrecioDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalleListaPrecioDTO update(DetalleListaPrecioDTO detalleListaPrecioDTO) {
        log.debug("Request to update DetalleListaPrecio : {}", detalleListaPrecioDTO);
        DetalleListaPrecio detalleListaPrecio = detalleListaPrecioMapper.toEntity(detalleListaPrecioDTO);
        detalleListaPrecio = detalleListaPrecioRepository.save(detalleListaPrecio);
        return detalleListaPrecioMapper.toDto(detalleListaPrecio);
    }

    /**
     * Partially update a detalleListaPrecio.
     *
     * @param detalleListaPrecioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetalleListaPrecioDTO> partialUpdate(DetalleListaPrecioDTO detalleListaPrecioDTO) {
        log.debug("Request to partially update DetalleListaPrecio : {}", detalleListaPrecioDTO);

        return detalleListaPrecioRepository
            .findById(detalleListaPrecioDTO.getId())
            .map(existingDetalleListaPrecio -> {
                detalleListaPrecioMapper.partialUpdate(existingDetalleListaPrecio, detalleListaPrecioDTO);

                return existingDetalleListaPrecio;
            })
            .map(detalleListaPrecioRepository::save)
            .map(detalleListaPrecioMapper::toDto);
    }

    /**
     * Get all the detalleListaPrecios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DetalleListaPrecioDTO> findAll() {
        log.debug("Request to get all DetalleListaPrecios");
        return detalleListaPrecioRepository
            .findAll()
            .stream()
            .map(detalleListaPrecioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the detalleListaPrecios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DetalleListaPrecioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detalleListaPrecioRepository.findAllWithEagerRelationships(pageable).map(detalleListaPrecioMapper::toDto);
    }

    /**
     * Get one detalleListaPrecio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalleListaPrecioDTO> findOne(Long id) {
        log.debug("Request to get DetalleListaPrecio : {}", id);
        return detalleListaPrecioRepository.findOneWithEagerRelationships(id).map(detalleListaPrecioMapper::toDto);
    }

    /**
     * Delete the detalleListaPrecio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleListaPrecio : {}", id);
        detalleListaPrecioRepository.deleteById(id);
    }

    public List<DetalleListaPrecio> findAllByListaPrecioId(Long id) {
        log.debug("Request to find All DetalleListaPrecio by List Price Id");
        return detalleListaPrecioRepository.findAllByListaPrecioId(id);
    }
}
