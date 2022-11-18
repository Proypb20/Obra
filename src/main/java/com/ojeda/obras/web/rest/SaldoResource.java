package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.SaldoRepository;
import com.ojeda.obras.repository.SumTrxRepRepository;
import com.ojeda.obras.service.SaldoService;
import com.ojeda.obras.service.SumTrxRepQueryService;
import com.ojeda.obras.service.SumTrxRepService;
import com.ojeda.obras.service.criteria.SumTrxRepCriteria;
import com.ojeda.obras.service.dto.SaldoDTO;
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
public class SaldoResource {

    private final Logger log = LoggerFactory.getLogger(SaldoResource.class);

    private static final String ENTITY_NAME = "saldo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaldoService saldoService;

    private final SaldoRepository saldoRepository;

    public SaldoResource(SaldoService saldoService, SaldoRepository saldoRepository) {
        this.saldoService = saldoService;
        this.saldoRepository = saldoRepository;
    }

    /**
     * {@code GET  /saldo} : get all the saldos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saldo in body.
     */
    @GetMapping("/saldo")
    public ResponseEntity<List<SaldoDTO>> getAllSaldos() {
        log.debug("REST request to get Saldos");
        List<SaldoDTO> entityList = saldoService.findAll();
        return ResponseEntity.ok().body(entityList);
    }
}
