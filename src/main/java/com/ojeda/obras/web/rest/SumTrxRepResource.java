package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.SumTrxRepRepository;
import com.ojeda.obras.service.SumTrxRepQueryService;
import com.ojeda.obras.service.SumTrxRepService;
import com.ojeda.obras.service.criteria.SumTrxRepCriteria;
import com.ojeda.obras.service.dto.SumTrxRepDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.SumTrxRep}.
 */
@RestController
@RequestMapping("/api")
public class SumTrxRepResource {

    private final Logger log = LoggerFactory.getLogger(SumTrxRepResource.class);

    private static final String ENTITY_NAME = "sumTrxReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SumTrxRepService sumTrxRepService;

    private final SumTrxRepRepository sumTrxRepRepository;

    private final SumTrxRepQueryService sumTrxRepQueryService;

    public SumTrxRepResource(
        SumTrxRepService sumTrxRepService,
        SumTrxRepRepository sumTrxRepRepository,
        SumTrxRepQueryService sumTrxRepQueryService
    ) {
        this.sumTrxRepService = sumTrxRepService;
        this.sumTrxRepRepository = sumTrxRepRepository;
        this.sumTrxRepQueryService = sumTrxRepQueryService;
    }

    /**
     * {@code GET  /sumTrxReps} : get all the advPendReps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptos in body.
     */
    @GetMapping("/sum-trx-rep")
    public ResponseEntity<List<SumTrxRepDTO>> getAllSumTrxReps(SumTrxRepCriteria criteria) {
        log.debug("REST request to get SumTrxReps by criteria: {}", criteria);
        List<SumTrxRepDTO> entityList = sumTrxRepQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
}
