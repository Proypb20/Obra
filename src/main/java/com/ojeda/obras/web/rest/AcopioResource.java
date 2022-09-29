package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.AcopioRepository;
import com.ojeda.obras.service.AcopioQueryService;
import com.ojeda.obras.service.AcopioService;
import com.ojeda.obras.service.criteria.AcopioCriteria;
import com.ojeda.obras.service.dto.AcopioDTO;
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
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.Acopio}.
 */
@RestController
@RequestMapping("/api")
public class AcopioResource {

    private final Logger log = LoggerFactory.getLogger(AcopioResource.class);

    private static final String ENTITY_NAME = "acopio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcopioService acopioService;

    private final AcopioRepository acopioRepository;

    private final AcopioQueryService acopioQueryService;

    public AcopioResource(AcopioService acopioService, AcopioRepository acopioRepository, AcopioQueryService acopioQueryService) {
        this.acopioService = acopioService;
        this.acopioRepository = acopioRepository;
        this.acopioQueryService = acopioQueryService;
    }

    /**
     * {@code POST  /acopios} : Create a new acopio.
     *
     * @param acopioDTO the acopioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acopioDTO, or with status {@code 400 (Bad Request)} if the acopio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acopios")
    public ResponseEntity<AcopioDTO> createAcopio(@RequestBody AcopioDTO acopioDTO) throws URISyntaxException {
        log.debug("REST request to save Acopio : {}", acopioDTO);
        if (acopioDTO.getId() != null) {
            throw new BadRequestAlertException("A new acopio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcopioDTO result = acopioService.save(acopioDTO);
        return ResponseEntity
            .created(new URI("/api/acopios/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acopios/:id} : Updates an existing acopio.
     *
     * @param id the id of the acopioDTO to save.
     * @param acopioDTO the acopioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acopioDTO,
     * or with status {@code 400 (Bad Request)} if the acopioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acopioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acopios/{id}")
    public ResponseEntity<AcopioDTO> updateAcopio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcopioDTO acopioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Acopio : {}, {}", id, acopioDTO);
        if (acopioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acopioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acopioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcopioDTO result = acopioService.update(acopioDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acopioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acopios/:id} : Partial updates given fields of an existing acopio, field will ignore if it is null
     *
     * @param id the id of the acopioDTO to save.
     * @param acopioDTO the acopioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acopioDTO,
     * or with status {@code 400 (Bad Request)} if the acopioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the acopioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the acopioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acopios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcopioDTO> partialUpdateAcopio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcopioDTO acopioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Acopio partially : {}, {}", id, acopioDTO);
        if (acopioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acopioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acopioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcopioDTO> result = acopioService.partialUpdate(acopioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acopioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acopios} : get all the acopios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acopios in body.
     */
    @GetMapping("/acopios")
    public ResponseEntity<List<AcopioDTO>> getAllAcopios(AcopioCriteria criteria) {
        log.debug("REST request to get Acopios by criteria: {}", criteria);
        List<AcopioDTO> entityList = acopioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /acopios/count} : count all the acopios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/acopios/count")
    public ResponseEntity<Long> countAcopios(AcopioCriteria criteria) {
        log.debug("REST request to count Acopios by criteria: {}", criteria);
        return ResponseEntity.ok().body(acopioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /acopios/:id} : get the "id" acopio.
     *
     * @param id the id of the acopioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acopioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acopios/{id}")
    public ResponseEntity<AcopioDTO> getAcopio(@PathVariable Long id) {
        log.debug("REST request to get Acopio : {}", id);
        Optional<AcopioDTO> acopioDTO = acopioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acopioDTO);
    }

    /**
     * {@code DELETE  /acopios/:id} : delete the "id" acopio.
     *
     * @param id the id of the acopioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acopios/{id}")
    public ResponseEntity<Void> deleteAcopio(@PathVariable Long id) {
        log.debug("REST request to delete Acopio : {}", id);
        acopioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
