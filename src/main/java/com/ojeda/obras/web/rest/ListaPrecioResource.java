package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.domain.XXHeaderUtil;
import com.ojeda.obras.repository.ListaPrecioRepository;
import com.ojeda.obras.service.DetalleListaPrecioService;
import com.ojeda.obras.service.ListaPrecioQueryService;
import com.ojeda.obras.service.ListaPrecioService;
import com.ojeda.obras.service.criteria.ListaPrecioCriteria;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
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
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.ListaPrecio}.
 */
@RestController
@RequestMapping("/api")
public class ListaPrecioResource {

    private final Logger log = LoggerFactory.getLogger(ListaPrecioResource.class);

    private static final String ENTITY_NAME = "listaPrecio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListaPrecioService listaPrecioService;

    private final ListaPrecioRepository listaPrecioRepository;

    private final ListaPrecioQueryService listaPrecioQueryService;

    private final DetalleListaPrecioService detalleListaPrecioService;

    public ListaPrecioResource(
        ListaPrecioService listaPrecioService,
        ListaPrecioRepository listaPrecioRepository,
        ListaPrecioQueryService listaPrecioQueryService,
        DetalleListaPrecioService detalleListaPrecioService
    ) {
        this.listaPrecioService = listaPrecioService;
        this.listaPrecioRepository = listaPrecioRepository;
        this.listaPrecioQueryService = listaPrecioQueryService;
        this.detalleListaPrecioService = detalleListaPrecioService;
    }

    /**
     * {@code POST  /lista-precios} : Create a new listaPrecio.
     *
     * @param listaPrecioDTO the listaPrecioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listaPrecioDTO, or with status {@code 400 (Bad Request)} if the listaPrecio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lista-precios")
    public ResponseEntity<ListaPrecioDTO> createListaPrecio(@Valid @RequestBody ListaPrecioDTO listaPrecioDTO) throws URISyntaxException {
        log.debug("REST request to save ListaPrecio : {}", listaPrecioDTO);
        if (listaPrecioDTO.getId() != null) {
            throw new BadRequestAlertException("A new listaPrecio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaPrecioDTO result = listaPrecioService.save(listaPrecioDTO);
        return ResponseEntity
            .created(new URI("/api/lista-precios/" + result.getId()))
            .headers(XXHeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/lista-precios/importXLS", consumes = { "multipart/form-data" })
    public ResponseEntity<?> write(
        @RequestParam(value = "file") MultipartFile multipartFile,
        @RequestParam(value = "idProv") Long idProveedor
    ) throws Exception {
        log.debug("REST request to Import file: {}", multipartFile);
        if (listaPrecioService.submitXLS(multipartFile, idProveedor)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new BadRequestAlertException("Error when try to generate XML", ENTITY_NAME, "XLSerrorWriting");
        }
    }

    /**
     * {@code PUT  /lista-precios/:id} : Updates an existing listaPrecio.
     *
     * @param id the id of the listaPrecioDTO to save.
     * @param listaPrecioDTO the listaPrecioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaPrecioDTO,
     * or with status {@code 400 (Bad Request)} if the listaPrecioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listaPrecioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lista-precios/{id}")
    public ResponseEntity<ListaPrecioDTO> updateListaPrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ListaPrecioDTO listaPrecioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ListaPrecio : {}, {}", id, listaPrecioDTO);
        if (listaPrecioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaPrecioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaPrecioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListaPrecioDTO result = listaPrecioService.update(listaPrecioDTO);
        return ResponseEntity
            .ok()
            .headers(XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, listaPrecioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lista-precios/:id} : Partial updates given fields of an existing listaPrecio, field will ignore if it is null
     *
     * @param id the id of the listaPrecioDTO to save.
     * @param listaPrecioDTO the listaPrecioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaPrecioDTO,
     * or with status {@code 400 (Bad Request)} if the listaPrecioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the listaPrecioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the listaPrecioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lista-precios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ListaPrecioDTO> partialUpdateListaPrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ListaPrecioDTO listaPrecioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListaPrecio partially : {}, {}", id, listaPrecioDTO);
        if (listaPrecioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaPrecioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaPrecioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListaPrecioDTO> result = listaPrecioService.partialUpdate(listaPrecioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            XXHeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, listaPrecioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lista-precios} : get all the listaPrecios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listaPrecios in body.
     */
    @GetMapping("/lista-precios")
    public ResponseEntity<List<ListaPrecioDTO>> getAllListaPrecios(ListaPrecioCriteria criteria) {
        log.debug("REST request to get ListaPrecios by criteria: {}", criteria);
        List<ListaPrecioDTO> entityList = listaPrecioQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /lista-precios/count} : count all the listaPrecios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lista-precios/count")
    public ResponseEntity<Long> countListaPrecios(ListaPrecioCriteria criteria) {
        log.debug("REST request to count ListaPrecios by criteria: {}", criteria);
        return ResponseEntity.ok().body(listaPrecioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lista-precios/:id} : get the "id" listaPrecio.
     *
     * @param id the id of the listaPrecioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listaPrecioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lista-precios/{id}")
    public ResponseEntity<ListaPrecioDTO> getListaPrecio(@PathVariable Long id) {
        log.debug("REST request to get ListaPrecio : {}", id);
        Optional<ListaPrecioDTO> listaPrecioDTO = listaPrecioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listaPrecioDTO);
    }

    /**
     * {@code DELETE  /lista-precios/:id} : delete the "id" listaPrecio.
     *
     * @param id the id of the listaPrecioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lista-precios/{id}")
    public ResponseEntity<Void> deleteListaPrecio(@PathVariable Long id) {
        log.debug("REST request to delete ListaPrecio : {}", id);
        log.debug("Delete All Detail List Price");
        List<DetalleListaPrecio> dets = detalleListaPrecioService.findAllByListaPrecioId(id);
        for (DetalleListaPrecio dtlp : dets) {
            detalleListaPrecioService.delete(dtlp.getId());
        }
        listaPrecioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(XXHeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
