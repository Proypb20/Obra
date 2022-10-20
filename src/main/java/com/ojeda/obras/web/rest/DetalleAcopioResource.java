package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.DetalleAcopioRepository;
import com.ojeda.obras.service.DetalleAcopioQueryService;
import com.ojeda.obras.service.DetalleAcopioService;
import com.ojeda.obras.service.criteria.DetalleAcopioCriteria;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.DetalleAcopio}.
 */
@RestController
@RequestMapping("/api")
public class DetalleAcopioResource {

    private final Logger log = LoggerFactory.getLogger(DetalleAcopioResource.class);

    private static final String ENTITY_NAME = "detalleAcopio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalleAcopioService detalleAcopioService;

    private final DetalleAcopioRepository detalleAcopioRepository;

    private final DetalleAcopioQueryService detalleAcopioQueryService;

    public DetalleAcopioResource(
        DetalleAcopioService detalleAcopioService,
        DetalleAcopioRepository detalleAcopioRepository,
        DetalleAcopioQueryService detalleAcopioQueryService
    ) {
        this.detalleAcopioService = detalleAcopioService;
        this.detalleAcopioRepository = detalleAcopioRepository;
        this.detalleAcopioQueryService = detalleAcopioQueryService;
    }

    /**
     * {@code POST  /detalle-acopios} : Create a new detalleAcopio.
     *
     * @param detalleAcopioDTO the detalleAcopioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleAcopioDTO, or with status {@code 400 (Bad Request)} if the detalleAcopio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-acopios")
    public ResponseEntity<DetalleAcopioDTO> createDetalleAcopio(@Valid @RequestBody DetalleAcopioDTO detalleAcopioDTO)
        throws URISyntaxException {
        log.debug("REST request to save DetalleAcopio : {}", detalleAcopioDTO);
        if (detalleAcopioDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleAcopio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleAcopioDTO result = detalleAcopioService.save(detalleAcopioDTO);
        return ResponseEntity
            .created(new URI("/api/detalle-acopios/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-acopios/:id} : Updates an existing detalleAcopio.
     *
     * @param id the id of the detalleAcopioDTO to save.
     * @param detalleAcopioDTO the detalleAcopioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleAcopioDTO,
     * or with status {@code 400 (Bad Request)} if the detalleAcopioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalleAcopioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-acopios/{id}")
    public ResponseEntity<DetalleAcopioDTO> updateDetalleAcopio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetalleAcopioDTO detalleAcopioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetalleAcopio : {}, {}", id, detalleAcopioDTO);
        if (detalleAcopioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalleAcopioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalleAcopioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetalleAcopioDTO result = detalleAcopioService.update(detalleAcopioDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalleAcopioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalle-acopios/:id} : Partial updates given fields of an existing detalleAcopio, field will ignore if it is null
     *
     * @param id the id of the detalleAcopioDTO to save.
     * @param detalleAcopioDTO the detalleAcopioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleAcopioDTO,
     * or with status {@code 400 (Bad Request)} if the detalleAcopioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detalleAcopioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalleAcopioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalle-acopios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetalleAcopioDTO> partialUpdateDetalleAcopio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetalleAcopioDTO detalleAcopioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetalleAcopio partially : {}, {}", id, detalleAcopioDTO);
        if (detalleAcopioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalleAcopioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalleAcopioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetalleAcopioDTO> result = detalleAcopioService.partialUpdate(detalleAcopioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detalleAcopioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalle-acopios} : get all the detalleAcopios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleAcopios in body.
     */
    @GetMapping("/detalle-acopios")
    public ResponseEntity<List<DetalleAcopioDTO>> getAllDetalleAcopios(DetalleAcopioCriteria criteria) {
        log.debug("REST request to get DetalleAcopios by criteria: {}", criteria);
        List<DetalleAcopioDTO> entityList = detalleAcopioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /detalle-acopios/count} : count all the detalleAcopios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/detalle-acopios/count")
    public ResponseEntity<Long> countDetalleAcopios(DetalleAcopioCriteria criteria) {
        log.debug("REST request to count DetalleAcopios by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalleAcopioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalle-acopios/:id} : get the "id" detalleAcopio.
     *
     * @param id the id of the detalleAcopioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleAcopioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-acopios/{id}")
    public ResponseEntity<DetalleAcopioDTO> getDetalleAcopio(@PathVariable Long id) {
        log.debug("REST request to get DetalleAcopio : {}", id);
        Optional<DetalleAcopioDTO> detalleAcopioDTO = detalleAcopioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleAcopioDTO);
    }

    /**
     * {@code DELETE  /detalle-acopios/:id} : delete the "id" detalleAcopio.
     *
     * @param id the id of the detalleAcopioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-acopios/{id}")
    public ResponseEntity<Void> deleteDetalleAcopio(@PathVariable Long id) {
        log.debug("REST request to delete DetalleAcopio : {}", id);
        detalleAcopioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
