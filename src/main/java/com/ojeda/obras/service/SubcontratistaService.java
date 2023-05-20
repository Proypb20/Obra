package com.ojeda.obras.service;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.repository.MovimientoRepository;
import com.ojeda.obras.repository.SubcontratistaRepository;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import com.ojeda.obras.service.mapper.SubcontratistaMapper;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subcontratista}.
 */
@Service
@Transactional
public class SubcontratistaService {

    private final Logger log = LoggerFactory.getLogger(SubcontratistaService.class);

    private final SubcontratistaRepository subcontratistaRepository;

    private final SubcontratistaMapper subcontratistaMapper;

    private final TareaRepository tareaRepository;

    private final MovimientoRepository movimientoRepository;

    public SubcontratistaService(
        SubcontratistaRepository subcontratistaRepository,
        SubcontratistaMapper subcontratistaMapper,
        MovimientoRepository movimientoRepository,
        TareaRepository tareaRepository
    ) {
        this.subcontratistaRepository = subcontratistaRepository;
        this.subcontratistaMapper = subcontratistaMapper;
        this.movimientoRepository = movimientoRepository;
        this.tareaRepository = tareaRepository;
    }

    /**
     * Save a subcontratista.
     *
     * @param subcontratistaDTO the entity to save.
     * @return the persisted entity.
     */
    public SubcontratistaDTO save(SubcontratistaDTO subcontratistaDTO) {
        log.debug("Request to save Subcontratista : {}", subcontratistaDTO);
        Subcontratista subcontratista = subcontratistaMapper.toEntity(subcontratistaDTO);
        subcontratista = subcontratistaRepository.save(subcontratista);
        return subcontratistaMapper.toDto(subcontratista);
    }

    /**
     * Update a subcontratista.
     *
     * @param subcontratistaDTO the entity to save.
     * @return the persisted entity.
     */
    public SubcontratistaDTO update(SubcontratistaDTO subcontratistaDTO) {
        log.debug("Request to update Subcontratista : {}", subcontratistaDTO);
        Subcontratista subcontratista = subcontratistaMapper.toEntity(subcontratistaDTO);
        subcontratista = subcontratistaRepository.save(subcontratista);
        return subcontratistaMapper.toDto(subcontratista);
    }

    /**
     * Partially update a subcontratista.
     *
     * @param subcontratistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubcontratistaDTO> partialUpdate(SubcontratistaDTO subcontratistaDTO) {
        log.debug("Request to partially update Subcontratista : {}", subcontratistaDTO);

        return subcontratistaRepository
            .findById(subcontratistaDTO.getId())
            .map(existingSubcontratista -> {
                subcontratistaMapper.partialUpdate(existingSubcontratista, subcontratistaDTO);

                return existingSubcontratista;
            })
            .map(subcontratistaRepository::save)
            .map(subcontratistaMapper::toDto);
    }

    /**
     * Get all the subcontratistas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubcontratistaDTO> findAll() {
        log.debug("Request to get all Subcontratistas");
        return subcontratistaRepository
            .findAll()
            .stream()
            .map(subcontratistaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one subcontratista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubcontratistaDTO> findOne(Long id) {
        log.debug("Request to get Subcontratista : {}", id);
        return subcontratistaRepository.findById(id).map(subcontratistaMapper::toDto);
    }

    /**
     * Delete the subcontratista by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subcontratista : {}", id);

        log.debug("Valido que el subcontratista no tenga tareas");

        Long tareas = tareaRepository.getCountBySubcontratistaId(id);
        if (tareas != 0) {
            throw new BadRequestAlertException(
                "Hay tareas asociados a esta Subcontratista",
                ENTITY_NAME,
                "Hay tareas asociadas al subcontratista"
            );
        }

        log.debug("Valido que el subcontratista no tenga Movimientos");

        Long movs = movimientoRepository.getCountBySubcontratistaId(id);
        if (movs != 0) {
            throw new BadRequestAlertException(
                "Hay movimientos asociados a este subcontratista",
                ENTITY_NAME,
                "Hay movimientos asociados al subcontratista"
            );
        }

        subcontratistaRepository.deleteById(id);
    }
}
