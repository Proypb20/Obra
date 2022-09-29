package com.ojeda.obras.service;

import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.repository.TipoComprobanteRepository;
import com.ojeda.obras.service.dto.TipoComprobanteDTO;
import com.ojeda.obras.service.mapper.TipoComprobanteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoComprobante}.
 */
@Service
@Transactional
public class TipoComprobanteService {

    private final Logger log = LoggerFactory.getLogger(TipoComprobanteService.class);

    private final TipoComprobanteRepository tipoComprobanteRepository;

    private final TipoComprobanteMapper tipoComprobanteMapper;

    public TipoComprobanteService(TipoComprobanteRepository tipoComprobanteRepository, TipoComprobanteMapper tipoComprobanteMapper) {
        this.tipoComprobanteRepository = tipoComprobanteRepository;
        this.tipoComprobanteMapper = tipoComprobanteMapper;
    }

    /**
     * Save a tipoComprobante.
     *
     * @param tipoComprobanteDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoComprobanteDTO save(TipoComprobanteDTO tipoComprobanteDTO) {
        log.debug("Request to save TipoComprobante : {}", tipoComprobanteDTO);
        TipoComprobante tipoComprobante = tipoComprobanteMapper.toEntity(tipoComprobanteDTO);
        tipoComprobante = tipoComprobanteRepository.save(tipoComprobante);
        return tipoComprobanteMapper.toDto(tipoComprobante);
    }

    /**
     * Update a tipoComprobante.
     *
     * @param tipoComprobanteDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoComprobanteDTO update(TipoComprobanteDTO tipoComprobanteDTO) {
        log.debug("Request to update TipoComprobante : {}", tipoComprobanteDTO);
        TipoComprobante tipoComprobante = tipoComprobanteMapper.toEntity(tipoComprobanteDTO);
        tipoComprobante = tipoComprobanteRepository.save(tipoComprobante);
        return tipoComprobanteMapper.toDto(tipoComprobante);
    }

    /**
     * Partially update a tipoComprobante.
     *
     * @param tipoComprobanteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoComprobanteDTO> partialUpdate(TipoComprobanteDTO tipoComprobanteDTO) {
        log.debug("Request to partially update TipoComprobante : {}", tipoComprobanteDTO);

        return tipoComprobanteRepository
            .findById(tipoComprobanteDTO.getId())
            .map(existingTipoComprobante -> {
                tipoComprobanteMapper.partialUpdate(existingTipoComprobante, tipoComprobanteDTO);

                return existingTipoComprobante;
            })
            .map(tipoComprobanteRepository::save)
            .map(tipoComprobanteMapper::toDto);
    }

    /**
     * Get all the tipoComprobantes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoComprobanteDTO> findAll() {
        log.debug("Request to get all TipoComprobantes");
        return tipoComprobanteRepository
            .findAll()
            .stream()
            .map(tipoComprobanteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tipoComprobante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoComprobanteDTO> findOne(Long id) {
        log.debug("Request to get TipoComprobante : {}", id);
        return tipoComprobanteRepository.findById(id).map(tipoComprobanteMapper::toDto);
    }

    /**
     * Delete the tipoComprobante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoComprobante : {}", id);
        tipoComprobanteRepository.deleteById(id);
    }
}
