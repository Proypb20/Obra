package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.DetalleListaPrecioRepository;
import com.ojeda.obras.service.DetalleListaPrecioQueryService;
import com.ojeda.obras.service.DetalleListaPrecioService;
import com.ojeda.obras.service.criteria.DetalleListaPrecioCriteria;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.DetalleListaPrecio}.
 */
@RestController
@RequestMapping("/api")
public class DetalleListaPrecioResource {

    private final Logger log = LoggerFactory.getLogger(DetalleListaPrecioResource.class);

    private static final String ENTITY_NAME = "detalleListaPrecio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalleListaPrecioService detalleListaPrecioService;

    private final DetalleListaPrecioRepository detalleListaPrecioRepository;

    private final DetalleListaPrecioQueryService detalleListaPrecioQueryService;

    public DetalleListaPrecioResource(
        DetalleListaPrecioService detalleListaPrecioService,
        DetalleListaPrecioRepository detalleListaPrecioRepository,
        DetalleListaPrecioQueryService detalleListaPrecioQueryService
    ) {
        this.detalleListaPrecioService = detalleListaPrecioService;
        this.detalleListaPrecioRepository = detalleListaPrecioRepository;
        this.detalleListaPrecioQueryService = detalleListaPrecioQueryService;
    }

    /**
     * {@code POST  /detalle-lista-precios} : Create a new detalleListaPrecio.
     *
     * @param detalleListaPrecioDTO the detalleListaPrecioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleListaPrecioDTO, or with status {@code 400 (Bad Request)} if the detalleListaPrecio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-lista-precios")
    public ResponseEntity<DetalleListaPrecioDTO> createDetalleListaPrecio(@RequestBody DetalleListaPrecioDTO detalleListaPrecioDTO)
        throws URISyntaxException {
        log.debug("REST request to save DetalleListaPrecio : {}", detalleListaPrecioDTO);
        if (detalleListaPrecioDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleListaPrecio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleListaPrecioDTO result = detalleListaPrecioService.save(detalleListaPrecioDTO);
        return ResponseEntity
            .created(new URI("/api/detalle-lista-precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-lista-precios/:id} : Updates an existing detalleListaPrecio.
     *
     * @param id the id of the detalleListaPrecioDTO to save.
     * @param detalleListaPrecioDTO the detalleListaPrecioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleListaPrecioDTO,
     * or with status {@code 400 (Bad Request)} if the detalleListaPrecioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalleListaPrecioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-lista-precios/{id}")
    public ResponseEntity<DetalleListaPrecioDTO> updateDetalleListaPrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalleListaPrecioDTO detalleListaPrecioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetalleListaPrecio : {}, {}", id, detalleListaPrecioDTO);
        if (detalleListaPrecioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalleListaPrecioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalleListaPrecioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetalleListaPrecioDTO result = detalleListaPrecioService.update(detalleListaPrecioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalleListaPrecioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalle-lista-precios/:id} : Partial updates given fields of an existing detalleListaPrecio, field will ignore if it is null
     *
     * @param id the id of the detalleListaPrecioDTO to save.
     * @param detalleListaPrecioDTO the detalleListaPrecioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleListaPrecioDTO,
     * or with status {@code 400 (Bad Request)} if the detalleListaPrecioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detalleListaPrecioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalleListaPrecioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalle-lista-precios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetalleListaPrecioDTO> partialUpdateDetalleListaPrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetalleListaPrecioDTO detalleListaPrecioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetalleListaPrecio partially : {}, {}", id, detalleListaPrecioDTO);
        if (detalleListaPrecioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalleListaPrecioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalleListaPrecioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetalleListaPrecioDTO> result = detalleListaPrecioService.partialUpdate(detalleListaPrecioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalleListaPrecioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalle-lista-precios} : get all the detalleListaPrecios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleListaPrecios in body.
     */
    @GetMapping("/detalle-lista-precios")
    public ResponseEntity<List<DetalleListaPrecioDTO>> getAllDetalleListaPrecios(DetalleListaPrecioCriteria criteria) {
        log.debug("REST request to get DetalleListaPrecios by criteria: {}", criteria);
        List<DetalleListaPrecioDTO> entityList = detalleListaPrecioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /detalle-lista-precios/count} : count all the detalleListaPrecios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/detalle-lista-precios/count")
    public ResponseEntity<Long> countDetalleListaPrecios(DetalleListaPrecioCriteria criteria) {
        log.debug("REST request to count DetalleListaPrecios by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalleListaPrecioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalle-lista-precios/:id} : get the "id" detalleListaPrecio.
     *
     * @param id the id of the detalleListaPrecioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleListaPrecioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-lista-precios/{id}")
    public ResponseEntity<DetalleListaPrecioDTO> getDetalleListaPrecio(@PathVariable Long id) {
        log.debug("REST request to get DetalleListaPrecio : {}", id);
        Optional<DetalleListaPrecioDTO> detalleListaPrecioDTO = detalleListaPrecioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleListaPrecioDTO);
    }

    /**
     * {@code DELETE  /detalle-lista-precios/:id} : delete the "id" detalleListaPrecio.
     *
     * @param id the id of the detalleListaPrecioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-lista-precios/{id}")
    public ResponseEntity<Void> deleteDetalleListaPrecio(@PathVariable Long id) {
        log.debug("REST request to delete DetalleListaPrecio : {}", id);
        detalleListaPrecioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
