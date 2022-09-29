package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.ProvinciaRepository;
import com.ojeda.obras.service.ProvinciaQueryService;
import com.ojeda.obras.service.ProvinciaService;
import com.ojeda.obras.service.criteria.ProvinciaCriteria;
import com.ojeda.obras.service.dto.ProvinciaDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Provincia}.
 */
@RestController
@RequestMapping("/api")
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);

    private static final String ENTITY_NAME = "provincia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinciaService provinciaService;

    private final ProvinciaRepository provinciaRepository;

    private final ProvinciaQueryService provinciaQueryService;

    public ProvinciaResource(
        ProvinciaService provinciaService,
        ProvinciaRepository provinciaRepository,
        ProvinciaQueryService provinciaQueryService
    ) {
        this.provinciaService = provinciaService;
        this.provinciaRepository = provinciaRepository;
        this.provinciaQueryService = provinciaQueryService;
    }

    /**
     * {@code POST  /provincias} : Create a new provincia.
     *
     * @param provinciaDTO the provinciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provinciaDTO, or with status {@code 400 (Bad Request)} if the provincia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provincias")
    public ResponseEntity<ProvinciaDTO> createProvincia(@Valid @RequestBody ProvinciaDTO provinciaDTO) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provinciaDTO);
        if (provinciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new provincia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvinciaDTO result = provinciaService.save(provinciaDTO);
        return ResponseEntity
            .created(new URI("/api/provincias/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provincias/:id} : Updates an existing provincia.
     *
     * @param id the id of the provinciaDTO to save.
     * @param provinciaDTO the provinciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinciaDTO,
     * or with status {@code 400 (Bad Request)} if the provinciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provinciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provincias/{id}")
    public ResponseEntity<ProvinciaDTO> updateProvincia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProvinciaDTO provinciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}, {}", id, provinciaDTO);
        if (provinciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provinciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProvinciaDTO result = provinciaService.update(provinciaDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, provinciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /provincias/:id} : Partial updates given fields of an existing provincia, field will ignore if it is null
     *
     * @param id the id of the provinciaDTO to save.
     * @param provinciaDTO the provinciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinciaDTO,
     * or with status {@code 400 (Bad Request)} if the provinciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the provinciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the provinciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/provincias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProvinciaDTO> partialUpdateProvincia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProvinciaDTO provinciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provincia partially : {}, {}", id, provinciaDTO);
        if (provinciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provinciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProvinciaDTO> result = provinciaService.partialUpdate(provinciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, provinciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /provincias} : get all the provincias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provincias in body.
     */
    @GetMapping("/provincias")
    public ResponseEntity<List<ProvinciaDTO>> getAllProvincias(ProvinciaCriteria criteria) {
        log.debug("REST request to get Provincias by criteria: {}", criteria);
        List<ProvinciaDTO> entityList = provinciaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /provincias/count} : count all the provincias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/provincias/count")
    public ResponseEntity<Long> countProvincias(ProvinciaCriteria criteria) {
        log.debug("REST request to count Provincias by criteria: {}", criteria);
        return ResponseEntity.ok().body(provinciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /provincias/:id} : get the "id" provincia.
     *
     * @param id the id of the provinciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provinciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provincias/{id}")
    public ResponseEntity<ProvinciaDTO> getProvincia(@PathVariable Long id) {
        log.debug("REST request to get Provincia : {}", id);
        Optional<ProvinciaDTO> provinciaDTO = provinciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provinciaDTO);
    }

    /**
     * {@code DELETE  /provincias/:id} : delete the "id" provincia.
     *
     * @param id the id of the provinciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provincias/{id}")
    public ResponseEntity<Void> deleteProvincia(@PathVariable Long id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
