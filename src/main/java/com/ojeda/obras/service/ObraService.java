package com.ojeda.obras.service;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.mapper.ObraMapper;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
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
 * Service Implementation for managing {@link Obra}.
 */
@Service
@Transactional
public class ObraService {

    private final Logger log = LoggerFactory.getLogger(ObraService.class);

    private final ObraRepository obraRepository;

    private final ObraMapper obraMapper;

    private final TareaRepository tareaRepository;

    public ObraService(ObraRepository obraRepository, ObraMapper obraMapper, TareaRepository tareaRepository) {
        this.obraRepository = obraRepository;
        this.obraMapper = obraMapper;
        this.tareaRepository = tareaRepository;
    }

    /**
     * Save a obra.
     *
     * @param obraDTO the entity to save.
     * @return the persisted entity.
     */
    public ObraDTO save(ObraDTO obraDTO) {
        log.debug("Request to save Obra : {}", obraDTO);
        Obra obra = obraMapper.toEntity(obraDTO);
        obra = obraRepository.save(obra);
        return obraMapper.toDto(obra);
    }

    /**
     * Update a obra.
     *
     * @param obraDTO the entity to save.
     * @return the persisted entity.
     */
    public ObraDTO update(ObraDTO obraDTO) {
        log.debug("Request to update Obra : {}", obraDTO);
        Obra obra = obraMapper.toEntity(obraDTO);
        obra = obraRepository.save(obra);
        return obraMapper.toDto(obra);
    }

    /**
     * Partially update a obra.
     *
     * @param obraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ObraDTO> partialUpdate(ObraDTO obraDTO) {
        log.debug("Request to partially update Obra : {}", obraDTO);

        return obraRepository
            .findById(obraDTO.getId())
            .map(existingObra -> {
                obraMapper.partialUpdate(existingObra, obraDTO);

                return existingObra;
            })
            .map(obraRepository::save)
            .map(obraMapper::toDto);
    }

    /**
     * Get all the obras.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ObraDTO> findAll() {
        log.debug("Request to get all Obras");
        return obraRepository.findAll().stream().map(obraMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the obras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ObraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return obraRepository.findAllWithEagerRelationships(pageable).map(obraMapper::toDto);
    }

    /**
     * Get one obra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObraDTO> findOne(Long id) {
        log.debug("Request to get Obra : {}", id);
        return obraRepository.findOneWithEagerRelationships(id).map(obraMapper::toDto);
    }

    /**
     * Delete the obra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Obra : {}", id);

        log.debug("Valido que la obra no tenga tareas");

        Long tareas = tareaRepository.getCountByObraId(id);
        if (tareas != 0) {
            throw new BadRequestAlertException("Hay tareas asociados a esta obra", ENTITY_NAME, "Hay tareas asociadas a la obra");
        }
        obraRepository.deleteById(id);
    }
}
