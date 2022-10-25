package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.AdvObraRepRepository;
import com.ojeda.obras.service.AdvObraRepQueryService;
import com.ojeda.obras.service.AdvObraRepService;
import com.ojeda.obras.service.criteria.AdvObraRepCriteria;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.AdvObraRep}.
 */
@RestController
@RequestMapping("/api")
public class advObraRepResource {

    private final Logger log = LoggerFactory.getLogger(advObraRepResource.class);

    private static final String ENTITY_NAME = "advObraRep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvObraRepService advObraRepService;

    private final AdvObraRepRepository advObraRepRepository;

    private final AdvObraRepQueryService advObraRepQueryService;

    public advObraRepResource(
        AdvObraRepService advObraRepService,
        AdvObraRepRepository advObraRepRepository,
        AdvObraRepQueryService advObraRepQueryService
    ) {
        this.advObraRepService = advObraRepService;
        this.advObraRepRepository = advObraRepRepository;
        this.advObraRepQueryService = advObraRepQueryService;
    }

    /**
     * {@code GET  /advObraReps} : get all the advObraReps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advObraReps in body.
     */
    @GetMapping("/adv-obra-rep")
    public ResponseEntity<List<AdvObraRepDTO>> getAllAdvObraReps(AdvObraRepCriteria criteria) {
        log.debug("REST request to get AdvObraReps by criteria: {}", criteria);
        List<AdvObraRepDTO> entityList = advObraRepQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
}
