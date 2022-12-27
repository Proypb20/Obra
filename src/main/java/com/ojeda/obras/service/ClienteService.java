package com.ojeda.obras.service;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import com.ojeda.obras.domain.Cliente;
import com.ojeda.obras.repository.ClienteRepository;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.service.dto.ClienteDTO;
import com.ojeda.obras.service.mapper.ClienteMapper;
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
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    private final ObraRepository obraRepository;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, ObraRepository obraRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.obraRepository = obraRepository;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    /**
     * Update a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteDTO update(ClienteDTO clienteDTO) {
        log.debug("Request to update Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    /**
     * Partially update a cliente.
     *
     * @param clienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClienteDTO> partialUpdate(ClienteDTO clienteDTO) {
        log.debug("Request to partially update Cliente : {}", clienteDTO);

        return clienteRepository
            .findById(clienteDTO.getId())
            .map(existingCliente -> {
                clienteMapper.partialUpdate(existingCliente, clienteDTO);

                return existingCliente;
            })
            .map(clienteRepository::save)
            .map(clienteMapper::toDto);
    }

    /**
     * Get all the clientes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll().stream().map(clienteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the clientes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClienteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clienteRepository.findAllWithEagerRelationships(pageable).map(clienteMapper::toDto);
    }

    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findOneWithEagerRelationships(id).map(clienteMapper::toDto);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);

        log.debug("Valido que el cliente no tenga Obras");

        Long obras = obraRepository.getCountByClienteId(id);
        if (obras != 0) {
            throw new BadRequestAlertException("Hay obras asociados a este cliente", ENTITY_NAME, "Hay Obras");
        }
        clienteRepository.deleteById(id);
    }
}
