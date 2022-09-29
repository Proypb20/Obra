package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.service.ObraQueryService;
import com.ojeda.obras.service.ObraService;
import com.ojeda.obras.service.criteria.ObraCriteria;
import com.ojeda.obras.service.dto.ObraDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Obra}.
 */
@RestController
@RequestMapping("/api")
public class ObraResource {

    private final Logger log = LoggerFactory.getLogger(ObraResource.class);

    private static final String ENTITY_NAME = "obra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObraService obraService;

    private final ObraRepository obraRepository;

    private final ObraQueryService obraQueryService;

    public ObraResource(ObraService obraService, ObraRepository obraRepository, ObraQueryService obraQueryService) {
        this.obraService = obraService;
        this.obraRepository = obraRepository;
        this.obraQueryService = obraQueryService;
    }

    /**
     * {@code POST  /obras} : Create a new obra.
     *
     * @param obraDTO the obraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obraDTO, or with status {@code 400 (Bad Request)} if the obra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obras")
    public ResponseEntity<ObraDTO> createObra(@Valid @RequestBody ObraDTO obraDTO) throws URISyntaxException {
        log.debug("REST request to save Obra : {}", obraDTO);
        if (obraDTO.getId() != null) {
            throw new BadRequestAlertException("A new obra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObraDTO result = obraService.save(obraDTO);
        return ResponseEntity
            .created(new URI("/api/obras/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obras/:id} : Updates an existing obra.
     *
     * @param id the id of the obraDTO to save.
     * @param obraDTO the obraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obraDTO,
     * or with status {@code 400 (Bad Request)} if the obraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obras/{id}")
    public ResponseEntity<ObraDTO> updateObra(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObraDTO obraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Obra : {}, {}", id, obraDTO);
        if (obraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObraDTO result = obraService.update(obraDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obraDTO.getName()))
            .body(result);
    }

    /**
     * {@code PATCH  /obras/:id} : Partial updates given fields of an existing obra, field will ignore if it is null
     *
     * @param id the id of the obraDTO to save.
     * @param obraDTO the obraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obraDTO,
     * or with status {@code 400 (Bad Request)} if the obraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the obraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the obraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/obras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObraDTO> partialUpdateObra(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObraDTO obraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Obra partially : {}, {}", id, obraDTO);
        if (obraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObraDTO> result = obraService.partialUpdate(obraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obraDTO.getName())
        );
    }

    /**
     * {@code GET  /obras} : get all the obras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obras in body.
     */
    @GetMapping("/obras")
    public ResponseEntity<List<ObraDTO>> getAllObras(ObraCriteria criteria) {
        log.debug("REST request to get Obras by criteria: {}", criteria);
        List<ObraDTO> entityList = obraQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /obras/count} : count all the obras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/obras/count")
    public ResponseEntity<Long> countObras(ObraCriteria criteria) {
        log.debug("REST request to count Obras by criteria: {}", criteria);
        return ResponseEntity.ok().body(obraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /obras/:id} : get the "id" obra.
     *
     * @param id the id of the obraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obras/{id}")
    public ResponseEntity<ObraDTO> getObra(@PathVariable Long id) {
        log.debug("REST request to get Obra : {}", id);
        Optional<ObraDTO> obraDTO = obraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obraDTO);
    }

    /**
     * {@code DELETE  /obras/:id} : delete the "id" obra.
     *
     * @param id the id of the obraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obras/{id}")
    public ResponseEntity<Void> deleteObra(@PathVariable Long id) {
        log.debug("REST request to delete Obra : {}", id);
        obraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
