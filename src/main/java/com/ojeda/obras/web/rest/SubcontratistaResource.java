package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.SubcontratistaRepository;
import com.ojeda.obras.service.SubcontratistaQueryService;
import com.ojeda.obras.service.SubcontratistaService;
import com.ojeda.obras.service.criteria.SubcontratistaCriteria;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Subcontratista}.
 */
@RestController
@RequestMapping("/api")
public class SubcontratistaResource {

    private final Logger log = LoggerFactory.getLogger(SubcontratistaResource.class);

    private static final String ENTITY_NAME = "subcontratista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcontratistaService subcontratistaService;

    private final SubcontratistaRepository subcontratistaRepository;

    private final SubcontratistaQueryService subcontratistaQueryService;

    public SubcontratistaResource(
        SubcontratistaService subcontratistaService,
        SubcontratistaRepository subcontratistaRepository,
        SubcontratistaQueryService subcontratistaQueryService
    ) {
        this.subcontratistaService = subcontratistaService;
        this.subcontratistaRepository = subcontratistaRepository;
        this.subcontratistaQueryService = subcontratistaQueryService;
    }

    /**
     * {@code POST  /subcontratistas} : Create a new subcontratista.
     *
     * @param subcontratistaDTO the subcontratistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subcontratistaDTO, or with status {@code 400 (Bad Request)} if the subcontratista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subcontratistas")
    public ResponseEntity<SubcontratistaDTO> createSubcontratista(@Valid @RequestBody SubcontratistaDTO subcontratistaDTO)
        throws URISyntaxException {
        log.debug("REST request to save Subcontratista : {}", subcontratistaDTO);
        if (subcontratistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new subcontratista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubcontratistaDTO result = subcontratistaService.save(subcontratistaDTO);
        return ResponseEntity
            .created(new URI("/api/subcontratistas/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subcontratistas/:id} : Updates an existing subcontratista.
     *
     * @param id the id of the subcontratistaDTO to save.
     * @param subcontratistaDTO the subcontratistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcontratistaDTO,
     * or with status {@code 400 (Bad Request)} if the subcontratistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subcontratistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subcontratistas/{id}")
    public ResponseEntity<SubcontratistaDTO> updateSubcontratista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubcontratistaDTO subcontratistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subcontratista : {}, {}", id, subcontratistaDTO);
        if (subcontratistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subcontratistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subcontratistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubcontratistaDTO result = subcontratistaService.update(subcontratistaDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subcontratistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subcontratistas/:id} : Partial updates given fields of an existing subcontratista, field will ignore if it is null
     *
     * @param id the id of the subcontratistaDTO to save.
     * @param subcontratistaDTO the subcontratistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcontratistaDTO,
     * or with status {@code 400 (Bad Request)} if the subcontratistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subcontratistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subcontratistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subcontratistas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubcontratistaDTO> partialUpdateSubcontratista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubcontratistaDTO subcontratistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subcontratista partially : {}, {}", id, subcontratistaDTO);
        if (subcontratistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subcontratistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subcontratistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubcontratistaDTO> result = subcontratistaService.partialUpdate(subcontratistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subcontratistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subcontratistas} : get all the subcontratistas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subcontratistas in body.
     */
    @GetMapping("/subcontratistas")
    public ResponseEntity<List<SubcontratistaDTO>> getAllSubcontratistas(SubcontratistaCriteria criteria) {
        log.debug("REST request to get Subcontratistas by criteria: {}", criteria);
        List<SubcontratistaDTO> entityList = subcontratistaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /subcontratistas/count} : count all the subcontratistas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/subcontratistas/count")
    public ResponseEntity<Long> countSubcontratistas(SubcontratistaCriteria criteria) {
        log.debug("REST request to count Subcontratistas by criteria: {}", criteria);
        return ResponseEntity.ok().body(subcontratistaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subcontratistas/:id} : get the "id" subcontratista.
     *
     * @param id the id of the subcontratistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subcontratistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subcontratistas/{id}")
    public ResponseEntity<SubcontratistaDTO> getSubcontratista(@PathVariable Long id) {
        log.debug("REST request to get Subcontratista : {}", id);
        Optional<SubcontratistaDTO> subcontratistaDTO = subcontratistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subcontratistaDTO);
    }

    /**
     * {@code DELETE  /subcontratistas/:id} : delete the "id" subcontratista.
     *
     * @param id the id of the subcontratistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subcontratistas/{id}")
    public ResponseEntity<Void> deleteSubcontratista(@PathVariable Long id) {
        log.debug("REST request to delete Subcontratista : {}", id);
        subcontratistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
