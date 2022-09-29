package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.AdvPendRepRepository;
import com.ojeda.obras.service.AdvPendRepQueryService;
import com.ojeda.obras.service.AdvPendRepService;
import com.ojeda.obras.service.criteria.AdvPendRepCriteria;
import com.ojeda.obras.service.dto.AdvPendRepDTO;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.Concepto}.
 */
@RestController
@RequestMapping("/api")
public class AdvPendRepResource {

    private final Logger log = LoggerFactory.getLogger(AdvPendRepResource.class);

    private static final String ENTITY_NAME = "advancePendingReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvPendRepService advPendRepService;

    private final AdvPendRepRepository advPendRepRepository;

    private final AdvPendRepQueryService advPendRepQueryService;

    public AdvPendRepResource(
        AdvPendRepService advPendRepService,
        AdvPendRepRepository advPendRepRepository,
        AdvPendRepQueryService advPendRepQueryService
    ) {
        this.advPendRepService = advPendRepService;
        this.advPendRepRepository = advPendRepRepository;
        this.advPendRepQueryService = advPendRepQueryService;
    }

    /**
     * {@code GET  /advPendReps} : get all the advPendReps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptos in body.
     */
    @GetMapping("/adv-pending-rep")
    public ResponseEntity<List<AdvPendRepDTO>> getAllAdvPendReps(AdvPendRepCriteria criteria) {
        log.debug("REST request to get AdvPendReps by criteria: {}", criteria);
        List<AdvPendRepDTO> entityList = advPendRepQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
}
