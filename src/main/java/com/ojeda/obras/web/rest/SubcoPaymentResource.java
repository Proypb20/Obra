package com.ojeda.obras.web.rest;

import com.ojeda.obras.repository.SubcoPayAmountRepository;
import com.ojeda.obras.repository.SubcoPayConceptRepository;
import com.ojeda.obras.repository.SubcoPayPaymentRepository;
import com.ojeda.obras.service.*;
import com.ojeda.obras.service.dto.*;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.ojeda.obras.domain.Subcontratista}.
 */
@RestController
@RequestMapping("/api")
public class SubcoPaymentResource {

    private final Logger log = LoggerFactory.getLogger(SubcoPaymentResource.class);

    private static final String ENTITY_NAME = "SubcoPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcoPayConceptService subcoPayConceptService;

    private final SubcoPayAmountService subcoPayAmountService;

    private final SubcoPayPaymentService subcoPayPaymentService;

    private final SubcoPaymentService subcoPaymentService;

    private final SubcoPaySubcoService subcoPaySubcoService;

    private final SubcoPayConceptRepository subcoPayConceptRepository;

    private final SubcoPayAmountRepository subcoPayAmountRepository;

    private final SubcoPayPaymentRepository subcoPayPaymentRepository;

    public SubcoPaymentResource(
        SubcoPayConceptService subcoPayConceptService,
        SubcoPayAmountService subcoPayAmountService,
        SubcoPayPaymentService subcoPayPaymentService,
        SubcoPaymentService subcoPaymentService,
        SubcoPaySubcoService subcoPaySubcoService,
        SubcoPayConceptRepository subcoPayConceptRepository,
        SubcoPayAmountRepository subcoPayAmountRepository,
        SubcoPayPaymentRepository subcoPayPaymentRepository
    ) {
        this.subcoPayConceptService = subcoPayConceptService;
        this.subcoPayAmountService = subcoPayAmountService;
        this.subcoPayPaymentService = subcoPayPaymentService;
        this.subcoPaySubcoService = subcoPaySubcoService;
        this.subcoPaymentService = subcoPaymentService;
        this.subcoPayConceptRepository = subcoPayConceptRepository;
        this.subcoPayAmountRepository = subcoPayAmountRepository;
        this.subcoPayPaymentRepository = subcoPayPaymentRepository;
    }

    /**
     * {@code GET  /subco-payments} : getAll.
     *
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping(value = "/subco-payments/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateFile(@PathVariable Long id, HttpServletResponse response) throws IOException, URISyntaxException {
        log.debug("REST request to Generate a File for Pagos a Subcontratistas by Id: {}", id);

        List<SubcoPayConceptDTO> subcoPayConcepts = subcoPayConceptService.findAll();
        List<SubcoPaySubcoDTO> subcoPaySubcos = new ArrayList<>();
        if (id == 0) {
            subcoPaySubcos = subcoPaySubcoService.findAll();
        } else {
            subcoPaySubcos = subcoPaySubcoService.findByObraId(id);
        }

        if (subcoPaySubcos.size() != 0) {
            File file = subcoPaymentService.generateFile(subcoPayConcepts, subcoPaySubcos);
            response.setHeader("Content-Disposition", "attachment; filename=".concat(file.getName()));
            return ResponseEntity.ok().body(Files.readAllBytes(file.toPath()));
        } else throw new BadRequestAlertException("No se han encontrado Tareas", ENTITY_NAME, "No se puede crear el archivo");
    }
}
