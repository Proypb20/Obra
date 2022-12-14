package com.ojeda.obras.service;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.repository.AcopioRepository;
import com.ojeda.obras.repository.ListaPrecioRepository;
import com.ojeda.obras.repository.ProveedorRepository;
import com.ojeda.obras.service.dto.ProveedorDTO;
import com.ojeda.obras.service.mapper.ProveedorMapper;
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
 * Service Implementation for managing {@link Proveedor}.
 */
@Service
@Transactional
public class ProveedorService {

    private final Logger log = LoggerFactory.getLogger(ProveedorService.class);

    private final ProveedorRepository proveedorRepository;

    private final ProveedorMapper proveedorMapper;

    private final AcopioRepository acopioRepository;

    private final ListaPrecioRepository listaPrecioRepository;

    public ProveedorService(
        ProveedorRepository proveedorRepository,
        ProveedorMapper proveedorMapper,
        AcopioRepository acopioRepository,
        ListaPrecioRepository listaPrecioRepository
    ) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
        this.acopioRepository = acopioRepository;
        this.listaPrecioRepository = listaPrecioRepository;
    }

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProveedorDTO save(ProveedorDTO proveedorDTO) {
        log.debug("Request to save Proveedor : {}", proveedorDTO);
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }

    /**
     * Update a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProveedorDTO update(ProveedorDTO proveedorDTO) {
        log.debug("Request to update Proveedor : {}", proveedorDTO);
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }

    /**
     * Partially update a proveedor.
     *
     * @param proveedorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProveedorDTO> partialUpdate(ProveedorDTO proveedorDTO) {
        log.debug("Request to partially update Proveedor : {}", proveedorDTO);

        return proveedorRepository
            .findById(proveedorDTO.getId())
            .map(existingProveedor -> {
                proveedorMapper.partialUpdate(existingProveedor, proveedorDTO);

                return existingProveedor;
            })
            .map(proveedorRepository::save)
            .map(proveedorMapper::toDto);
    }

    /**
     * Get all the proveedors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProveedorDTO> findAll() {
        log.debug("Request to get all Proveedors");
        return proveedorRepository.findAll().stream().map(proveedorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the proveedors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProveedorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return proveedorRepository.findAllWithEagerRelationships(pageable).map(proveedorMapper::toDto);
    }

    /**
     * Get one proveedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProveedorDTO> findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        return proveedorRepository.findOneWithEagerRelationships(id).map(proveedorMapper::toDto);
    }

    /**
     * Delete the proveedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);

        log.debug("Valido que el proveedor no tenga Acopios");

        Long acopios = acopioRepository.getCountByProveedorId(id);
        if (acopios != 0) {
            throw new BadRequestAlertException("Hay acopios asociados a este proveedor", ENTITY_NAME, "hayacopios");
        }

        log.debug("Valido que el proveedor no tenga Listas de Precio");
        Long lps = listaPrecioRepository.getCountByProveedorId(id);
        if (lps != 0) {
            throw new BadRequestAlertException("Hay listas de Precio asociadas a este proveedor", ENTITY_NAME, "haylistaprecios");
        }

        proveedorRepository.deleteById(id);
    }
}
