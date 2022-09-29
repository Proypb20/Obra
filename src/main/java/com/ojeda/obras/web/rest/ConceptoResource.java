package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.ConceptoRepository;
import com.ojeda.obras.service.ConceptoQueryService;
import com.ojeda.obras.service.ConceptoService;
import com.ojeda.obras.service.criteria.ConceptoCriteria;
import com.ojeda.obras.service.dto.ConceptoDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Concepto}.
 */
@RestController
@RequestMapping("/api")
public class ConceptoResource {

    private final Logger log = LoggerFactory.getLogger(ConceptoResource.class);

    private static final String ENTITY_NAME = "concepto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptoService conceptoService;

    private final ConceptoRepository conceptoRepository;

    private final ConceptoQueryService conceptoQueryService;

    public ConceptoResource(
        ConceptoService conceptoService,
        ConceptoRepository conceptoRepository,
        ConceptoQueryService conceptoQueryService
    ) {
        this.conceptoService = conceptoService;
        this.conceptoRepository = conceptoRepository;
        this.conceptoQueryService = conceptoQueryService;
    }

    /**
     * {@code POST  /conceptos} : Create a new concepto.
     *
     * @param conceptoDTO the conceptoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptoDTO, or with status {@code 400 (Bad Request)} if the concepto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conceptos")
    public ResponseEntity<ConceptoDTO> createConcepto(@Valid @RequestBody ConceptoDTO conceptoDTO) throws URISyntaxException {
        log.debug("REST request to save Concepto : {}", conceptoDTO);
        if (conceptoDTO.getId() != null) {
            throw new BadRequestAlertException("A new concepto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptoDTO result = conceptoService.save(conceptoDTO);
        return ResponseEntity
            .created(new URI("/api/conceptos/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conceptos/:id} : Updates an existing concepto.
     *
     * @param id the id of the conceptoDTO to save.
     * @param conceptoDTO the conceptoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptoDTO,
     * or with status {@code 400 (Bad Request)} if the conceptoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conceptos/{id}")
    public ResponseEntity<ConceptoDTO> updateConcepto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConceptoDTO conceptoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Concepto : {}, {}", id, conceptoDTO);
        if (conceptoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conceptoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conceptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConceptoDTO result = conceptoService.update(conceptoDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conceptoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conceptos/:id} : Partial updates given fields of an existing concepto, field will ignore if it is null
     *
     * @param id the id of the conceptoDTO to save.
     * @param conceptoDTO the conceptoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptoDTO,
     * or with status {@code 400 (Bad Request)} if the conceptoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conceptoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conceptoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conceptos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConceptoDTO> partialUpdateConcepto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConceptoDTO conceptoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concepto partially : {}, {}", id, conceptoDTO);
        if (conceptoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conceptoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conceptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConceptoDTO> result = conceptoService.partialUpdate(conceptoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conceptoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conceptos} : get all the conceptos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptos in body.
     */
    @GetMapping("/conceptos")
    public ResponseEntity<List<ConceptoDTO>> getAllConceptos(ConceptoCriteria criteria) {
        log.debug("REST request to get Conceptos by criteria: {}", criteria);
        List<ConceptoDTO> entityList = conceptoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /conceptos/count} : count all the conceptos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/conceptos/count")
    public ResponseEntity<Long> countConceptos(ConceptoCriteria criteria) {
        log.debug("REST request to count Conceptos by criteria: {}", criteria);
        return ResponseEntity.ok().body(conceptoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conceptos/:id} : get the "id" concepto.
     *
     * @param id the id of the conceptoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conceptos/{id}")
    public ResponseEntity<ConceptoDTO> getConcepto(@PathVariable Long id) {
        log.debug("REST request to get Concepto : {}", id);
        Optional<ConceptoDTO> conceptoDTO = conceptoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conceptoDTO);
    }

    /**
     * {@code DELETE  /conceptos/:id} : delete the "id" concepto.
     *
     * @param id the id of the conceptoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conceptos/{id}")
    public ResponseEntity<Void> deleteConcepto(@PathVariable Long id) {
        log.debug("REST request to delete Concepto : {}", id);
        conceptoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
