package com.ojeda.obras.web.rest;

//import tech.jhipster.web.util.HeaderUtil;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.TareaQueryService;
import com.ojeda.obras.service.TareaService;
import com.ojeda.obras.service.criteria.TareaCriteria;
import com.ojeda.obras.service.dto.TareaDTO;
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
 * REST controller for managing {@link com.ojeda.obras.domain.Tarea}.
 */
@RestController
@RequestMapping("/api")
public class TareaResource {

    private final Logger log = LoggerFactory.getLogger(TareaResource.class);

    private static final String ENTITY_NAME = "tarea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TareaService tareaService;

    private final TareaRepository tareaRepository;

    private final TareaQueryService tareaQueryService;

    public TareaResource(TareaService tareaService, TareaRepository tareaRepository, TareaQueryService tareaQueryService) {
        this.tareaService = tareaService;
        this.tareaRepository = tareaRepository;
        this.tareaQueryService = tareaQueryService;
    }

    /**
     * {@code POST  /tareas} : Create a new tarea.
     *
     * @param tareaDTO the tareaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tareaDTO, or with status {@code 400 (Bad Request)} if the tarea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tareas")
    public ResponseEntity<TareaDTO> createTarea(@Valid @RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarea : {}", tareaDTO);
        if (tareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity
            .created(new URI("/api/tareas/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tareas/:id} : Updates an existing tarea.
     *
     * @param id the id of the tareaDTO to save.
     * @param tareaDTO the tareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tareaDTO,
     * or with status {@code 400 (Bad Request)} if the tareaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tareas/{id}")
    public ResponseEntity<TareaDTO> updateTarea(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TareaDTO tareaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tarea : {}, {}", id, tareaDTO);
        if (tareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tareaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tareaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TareaDTO result = tareaService.update(tareaDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tareas/:id} : Partial updates given fields of an existing tarea, field will ignore if it is null
     *
     * @param id the id of the tareaDTO to save.
     * @param tareaDTO the tareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tareaDTO,
     * or with status {@code 400 (Bad Request)} if the tareaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tareaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tareas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TareaDTO> partialUpdateTarea(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TareaDTO tareaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tarea partially : {}, {}", id, tareaDTO);
        if (tareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tareaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tareaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TareaDTO> result = tareaService.partialUpdate(tareaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tareaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tareas} : get all the tareas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tareas in body.
     */
    @GetMapping("/tareas")
    public ResponseEntity<List<TareaDTO>> getAllTareas(TareaCriteria criteria) {
        log.debug("REST request to get Tareas by criteria: {}", criteria);
        List<TareaDTO> entityList = tareaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tareas/count} : count all the tareas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tareas/count")
    public ResponseEntity<Long> countTareas(TareaCriteria criteria) {
        log.debug("REST request to count Tareas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tareaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tareas/:id} : get the "id" tarea.
     *
     * @param id the id of the tareaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tareaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaDTO> getTarea(@PathVariable Long id) {
        log.debug("REST request to get Tarea : {}", id);
        Optional<TareaDTO> tareaDTO = tareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tareaDTO);
    }

    /**
     * {@code DELETE  /tareas/:id} : delete the "id" tarea.
     *
     * @param id the id of the tareaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        log.debug("REST request to delete Tarea : {}", id);
        tareaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
