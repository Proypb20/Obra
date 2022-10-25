package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.TipoComprobanteRepository;
import com.ojeda.obras.service.TipoComprobanteQueryService;
import com.ojeda.obras.service.TipoComprobanteService;
import com.ojeda.obras.service.criteria.TipoComprobanteCriteria;
import com.ojeda.obras.service.dto.TipoComprobanteDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.TipoComprobante}.
 */
@RestController
@RequestMapping("/api")
public class TipoComprobanteResource {

    private final Logger log = LoggerFactory.getLogger(TipoComprobanteResource.class);

    private static final String ENTITY_NAME = "tipoComprobante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoComprobanteService tipoComprobanteService;

    private final TipoComprobanteRepository tipoComprobanteRepository;

    private final TipoComprobanteQueryService tipoComprobanteQueryService;

    public TipoComprobanteResource(
        TipoComprobanteService tipoComprobanteService,
        TipoComprobanteRepository tipoComprobanteRepository,
        TipoComprobanteQueryService tipoComprobanteQueryService
    ) {
        this.tipoComprobanteService = tipoComprobanteService;
        this.tipoComprobanteRepository = tipoComprobanteRepository;
        this.tipoComprobanteQueryService = tipoComprobanteQueryService;
    }

    /**
     * {@code POST  /tipo-comprobantes} : Create a new tipoComprobante.
     *
     * @param tipoComprobanteDTO the tipoComprobanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoComprobanteDTO, or with status {@code 400 (Bad Request)} if the tipoComprobante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-comprobantes")
    public ResponseEntity<TipoComprobanteDTO> createTipoComprobante(@Valid @RequestBody TipoComprobanteDTO tipoComprobanteDTO)
        throws URISyntaxException {
        log.debug("REST request to save TipoComprobante : {}", tipoComprobanteDTO);
        if (tipoComprobanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoComprobante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoComprobanteDTO result = tipoComprobanteService.save(tipoComprobanteDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-comprobantes/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getName()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-comprobantes/:id} : Updates an existing tipoComprobante.
     *
     * @param id the id of the tipoComprobanteDTO to save.
     * @param tipoComprobanteDTO the tipoComprobanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoComprobanteDTO,
     * or with status {@code 400 (Bad Request)} if the tipoComprobanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoComprobanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-comprobantes/{id}")
    public ResponseEntity<TipoComprobanteDTO> updateTipoComprobante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoComprobanteDTO tipoComprobanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoComprobante : {}, {}", id, tipoComprobanteDTO);
        if (tipoComprobanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoComprobanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoComprobanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoComprobanteDTO result = tipoComprobanteService.update(tipoComprobanteDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoComprobanteDTO.getName()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-comprobantes/:id} : Partial updates given fields of an existing tipoComprobante, field will ignore if it is null
     *
     * @param id the id of the tipoComprobanteDTO to save.
     * @param tipoComprobanteDTO the tipoComprobanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoComprobanteDTO,
     * or with status {@code 400 (Bad Request)} if the tipoComprobanteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoComprobanteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoComprobanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-comprobantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoComprobanteDTO> partialUpdateTipoComprobante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoComprobanteDTO tipoComprobanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoComprobante partially : {}, {}", id, tipoComprobanteDTO);
        if (tipoComprobanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoComprobanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoComprobanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoComprobanteDTO> result = tipoComprobanteService.partialUpdate(tipoComprobanteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoComprobanteDTO.getName())
        );
    }

    /**
     * {@code GET  /tipo-comprobantes} : get all the tipoComprobantes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoComprobantes in body.
     */
    @GetMapping("/tipo-comprobantes")
    public ResponseEntity<List<TipoComprobanteDTO>> getAllTipoComprobantes(TipoComprobanteCriteria criteria) {
        log.debug("REST request to get TipoComprobantes by criteria: {}", criteria);
        List<TipoComprobanteDTO> entityList = tipoComprobanteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tipo-comprobantes/count} : count all the tipoComprobantes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-comprobantes/count")
    public ResponseEntity<Long> countTipoComprobantes(TipoComprobanteCriteria criteria) {
        log.debug("REST request to count TipoComprobantes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoComprobanteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-comprobantes/:id} : get the "id" tipoComprobante.
     *
     * @param id the id of the tipoComprobanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoComprobanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-comprobantes/{id}")
    public ResponseEntity<TipoComprobanteDTO> getTipoComprobante(@PathVariable Long id) {
        log.debug("REST request to get TipoComprobante : {}", id);
        Optional<TipoComprobanteDTO> tipoComprobanteDTO = tipoComprobanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoComprobanteDTO);
    }

    /**
     * {@code DELETE  /tipo-comprobantes/:id} : delete the "id" tipoComprobante.
     *
     * @param id the id of the tipoComprobanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-comprobantes/{id}")
    public ResponseEntity<Void> deleteTipoComprobante(@PathVariable Long id) {
        log.debug("REST request to delete TipoComprobante : {}", id);
        Optional<TipoComprobanteDTO> tipoComprobante = tipoComprobanteService.findOne(id);
        tipoComprobanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, tipoComprobante.get().getName()))
            .build();
    }
}
