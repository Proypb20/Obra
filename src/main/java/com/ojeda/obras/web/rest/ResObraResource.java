package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.ResObra;
import com.ojeda.obras.repository.ResObraRepository;
import com.ojeda.obras.service.ResObraQueryService;
import com.ojeda.obras.service.ResObraService;
import com.ojeda.obras.service.criteria.ResObraCriteria;
import com.ojeda.obras.service.dto.ResObraDTO;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link ResObra}.
 */
@RestController
@RequestMapping("/api")
public class ResObraResource {

    private final Logger log = LoggerFactory.getLogger(ResObraResource.class);

    private static final String ENTITY_NAME = "resObra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResObraService resObraService;

    private final ResObraRepository resObraRepository;

    private final ResObraQueryService resObraQueryService;

    public ResObraResource(ResObraService resObraService, ResObraRepository resObraRepository, ResObraQueryService resObraQueryService) {
        this.resObraService = resObraService;
        this.resObraRepository = resObraRepository;
        this.resObraQueryService = resObraQueryService;
    }

    /**
     * {@code GET  /ResObras} : get all the ResObras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ResObras in body.
     */
    @GetMapping("/res-obra-rep")
    public ResponseEntity<List<ResObraDTO>> getAllResObras(ResObraCriteria criteria) {
        log.debug("REST request to get ResObras by criteria: {}", criteria);
        List<ResObraDTO> entityList = resObraQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /resObra-rep/exportXML"} : getAllResObrasReport
     *
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping(value = "/res-obra-rep/exportXML", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateFile(ResObraCriteria criteria, HttpServletResponse response)
        throws IOException, URISyntaxException {
        log.debug("REST request to get ResObras and Export by criteria: {}", criteria);
        List<ResObraDTO> resObras = resObraQueryService.findByCriteria(criteria);
        if (resObras.size() != 0) {
            File file = resObraService.generateFile(resObras, resObras.get(0).getObraName(), resObras.get(0).getPeriodName());
            response.setHeader("Content-Disposition", "attachment; filename=".concat(file.getName()));
            return ResponseEntity.ok().body(Files.readAllBytes(file.toPath()));
        } else throw new BadRequestAlertException("No se han encontrado Movimientos", ENTITY_NAME, "No se puede crear el archivo");
    }
}
