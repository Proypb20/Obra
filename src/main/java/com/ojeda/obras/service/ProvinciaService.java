package com.ojeda.obras.service;

import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.repository.ProvinciaRepository;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import com.ojeda.obras.service.mapper.ProvinciaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Provincia}.
 */
@Service
@Transactional
public class ProvinciaService {

    private final Logger log = LoggerFactory.getLogger(ProvinciaService.class);

    private final ProvinciaRepository provinciaRepository;

    private final ProvinciaMapper provinciaMapper;

    public ProvinciaService(ProvinciaRepository provinciaRepository, ProvinciaMapper provinciaMapper) {
        this.provinciaRepository = provinciaRepository;
        this.provinciaMapper = provinciaMapper;
    }

    /**
     * Save a provincia.
     *
     * @param provinciaDTO the entity to save.
     * @return the persisted entity.
     */
    public ProvinciaDTO save(ProvinciaDTO provinciaDTO) {
        log.debug("Request to save Provincia : {}", provinciaDTO);
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        provincia = provinciaRepository.save(provincia);
        return provinciaMapper.toDto(provincia);
    }

    /**
     * Update a provincia.
     *
     * @param provinciaDTO the entity to save.
     * @return the persisted entity.
     */
    public ProvinciaDTO update(ProvinciaDTO provinciaDTO) {
        log.debug("Request to update Provincia : {}", provinciaDTO);
        Provincia provincia = provinciaMapper.toEntity(provinciaDTO);
        provincia = provinciaRepository.save(provincia);
        return provinciaMapper.toDto(provincia);
    }

    /**
     * Partially update a provincia.
     *
     * @param provinciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProvinciaDTO> partialUpdate(ProvinciaDTO provinciaDTO) {
        log.debug("Request to partially update Provincia : {}", provinciaDTO);

        return provinciaRepository
            .findById(provinciaDTO.getId())
            .map(existingProvincia -> {
                provinciaMapper.partialUpdate(existingProvincia, provinciaDTO);

                return existingProvincia;
            })
            .map(provinciaRepository::save)
            .map(provinciaMapper::toDto);
    }

    /**
     * Get all the provincias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProvinciaDTO> findAll() {
        log.debug("Request to get all Provincias");
        return provinciaRepository.findAll().stream().map(provinciaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one provincia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProvinciaDTO> findOne(Long id) {
        log.debug("Request to get Provincia : {}", id);
        return provinciaRepository.findById(id).map(provinciaMapper::toDto);
    }

    /**
     * Delete the provincia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Provincia : {}", id);
        provinciaRepository.deleteById(id);
    }
}
