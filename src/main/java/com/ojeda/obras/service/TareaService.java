package com.ojeda.obras.service;

import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.dto.TareaDTO;
import com.ojeda.obras.service.mapper.TareaMapper;
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
 * Service Implementation for managing {@link Tarea}.
 */
@Service
@Transactional
public class TareaService {

    private final Logger log = LoggerFactory.getLogger(TareaService.class);

    private final TareaRepository tareaRepository;

    private final TareaMapper tareaMapper;

    public TareaService(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    /**
     * Save a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    public TareaDTO save(TareaDTO tareaDTO) {
        log.debug("Request to save Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    /**
     * Update a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    public TareaDTO update(TareaDTO tareaDTO) {
        log.debug("Request to update Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    /**
     * Partially update a tarea.
     *
     * @param tareaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TareaDTO> partialUpdate(TareaDTO tareaDTO) {
        log.debug("Request to partially update Tarea : {}", tareaDTO);

        return tareaRepository
            .findById(tareaDTO.getId())
            .map(existingTarea -> {
                tareaMapper.partialUpdate(existingTarea, tareaDTO);

                return existingTarea;
            })
            .map(tareaRepository::save)
            .map(tareaMapper::toDto);
    }

    /**
     * Get all the tareas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TareaDTO> findAll() {
        log.debug("Request to get all Tareas");
        return tareaRepository.findAll().stream().map(tareaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the tareas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TareaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tareaRepository.findAllWithEagerRelationships(pageable).map(tareaMapper::toDto);
    }

    /**
     * Get one tarea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TareaDTO> findOne(Long id) {
        log.debug("Request to get Tarea : {}", id);
        return tareaRepository.findOneWithEagerRelationships(id).map(tareaMapper::toDto);
    }

    /**
     * Delete the tarea by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tarea : {}", id);
        tareaRepository.deleteById(id);
    }
}
