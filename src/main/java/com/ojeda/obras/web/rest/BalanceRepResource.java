package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.BalanceRepRepository;
import com.ojeda.obras.service.BalanceRepQueryService;
import com.ojeda.obras.service.BalanceRepService;
import com.ojeda.obras.service.criteria.BalanceRepCriteria;
import com.ojeda.obras.service.dto.BalanceRepDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.BalanceRep}.
 */
@RestController
@RequestMapping("/api")
public class BalanceRepResource {

    private final Logger log = LoggerFactory.getLogger(BalanceRepResource.class);

    private static final String ENTITY_NAME = "balanceReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalanceRepService balanceRepService;

    private final BalanceRepRepository balanceRepRepository;

    private final BalanceRepQueryService balanceRepQueryService;

    public BalanceRepResource(
        BalanceRepService balanceRepService,
        BalanceRepRepository balanceRepRepository,
        BalanceRepQueryService balanceRepQueryService
    ) {
        this.balanceRepService = balanceRepService;
        this.balanceRepRepository = balanceRepRepository;
        this.balanceRepQueryService = balanceRepQueryService;
    }

    /**
     * {@code GET  /balanceReps} : get all the balanceReps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of BalanceRep in body.
     */
    @GetMapping("/balance-rep")
    public ResponseEntity<List<BalanceRepDTO>> getAllBalanceReps(BalanceRepCriteria criteria) {
        log.debug("REST request to get BalanceReps by criteria: {}", criteria);
        List<BalanceRepDTO> entityList = balanceRepQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
}
