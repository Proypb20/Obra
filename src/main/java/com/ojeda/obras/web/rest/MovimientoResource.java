package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.MovimientoRepository;
import com.ojeda.obras.service.MovimientoQueryService;
import com.ojeda.obras.service.MovimientoService;
import com.ojeda.obras.service.criteria.MovimientoCriteria;
import com.ojeda.obras.service.dto.MovimientoDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Movimiento}.
 */
@RestController
@RequestMapping("/api")
public class MovimientoResource {

    private final Logger log = LoggerFactory.getLogger(MovimientoResource.class);

    private static final String ENTITY_NAME = "movimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimientoService movimientoService;

    private final MovimientoRepository movimientoRepository;

    private final MovimientoQueryService movimientoQueryService;

    public MovimientoResource(
        MovimientoService movimientoService,
        MovimientoRepository movimientoRepository,
        MovimientoQueryService movimientoQueryService
    ) {
        this.movimientoService = movimientoService;
        this.movimientoRepository = movimientoRepository;
        this.movimientoQueryService = movimientoQueryService;
    }

    /**
     * {@code POST  /movimientos} : Create a new movimiento.
     *
     * @param movimientoDTO the movimientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimientoDTO, or with status {@code 400 (Bad Request)} if the movimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoDTO> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) throws URISyntaxException {
        log.debug("REST request to save Movimiento : {}", movimientoDTO);
        if (movimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovimientoDTO result = movimientoService.save(movimientoDTO);
        return ResponseEntity
            .created(new URI("/api/movimientos/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movimientos/:id} : Updates an existing movimiento.
     *
     * @param id the id of the movimientoDTO to save.
     * @param movimientoDTO the movimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimientoDTO,
     * or with status {@code 400 (Bad Request)} if the movimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoDTO> updateMovimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MovimientoDTO movimientoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Movimiento : {}, {}", id, movimientoDTO);
        if (movimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MovimientoDTO result = movimientoService.update(movimientoDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /movimientos/:id} : Partial updates given fields of an existing movimiento, field will ignore if it is null
     *
     * @param id the id of the movimientoDTO to save.
     * @param movimientoDTO the movimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimientoDTO,
     * or with status {@code 400 (Bad Request)} if the movimientoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the movimientoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the movimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/movimientos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MovimientoDTO> partialUpdateMovimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MovimientoDTO movimientoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movimiento partially : {}, {}", id, movimientoDTO);
        if (movimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movimientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!movimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MovimientoDTO> result = movimientoService.partialUpdate(movimientoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, movimientoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /movimientos} : get all the movimientos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimientos in body.
     */
    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos(MovimientoCriteria criteria) {
        log.debug("REST request to get Movimientos by criteria: {}", criteria);
        List<MovimientoDTO> entityList = movimientoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /movimientos/count} : count all the movimientos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/movimientos/count")
    public ResponseEntity<Long> countMovimientos(MovimientoCriteria criteria) {
        log.debug("REST request to count Movimientos by criteria: {}", criteria);
        return ResponseEntity.ok().body(movimientoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /movimientos/:id} : get the "id" movimiento.
     *
     * @param id the id of the movimientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoDTO> getMovimiento(@PathVariable Long id) {
        log.debug("REST request to get Movimiento : {}", id);
        Optional<MovimientoDTO> movimientoDTO = movimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimientoDTO);
    }

    /**
     * {@code DELETE  /movimientos/:id} : delete the "id" movimiento.
     *
     * @param id the id of the movimientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        log.debug("REST request to delete Movimiento : {}", id);
        movimientoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
