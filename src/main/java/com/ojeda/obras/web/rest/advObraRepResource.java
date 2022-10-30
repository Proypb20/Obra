package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.repository.AdvObraRepRepository;
import com.ojeda.obras.service.AdvObraRepQueryService;
import com.ojeda.obras.service.AdvObraRepService;
import com.ojeda.obras.service.criteria.AdvObraRepCriteria;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
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

    @GetMapping(value = "/adv-obra-rep/generateFile/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateFile(@PathVariable Long id, HttpServletResponse response) throws IOException, URISyntaxException {
        log.debug("REST request to Generate a File from: {}", id);
        if (id == null) {
            throw new BadRequestAlertException("A event cannot have an empty ID", ENTITY_NAME, "idexists");
        }
        List<AdvObraRep> advObraRep = advObraRepService.findAllByObraId(id);
        if (advObraRep.size() != 0) {
            File file = advObraRepService.generateFile(advObraRep);
            response.setHeader("Content-Disposition", "attachment; filename=".concat(file.getName()));
            return ResponseEntity.ok().body(Files.readAllBytes(file.toPath()));
        } else throw new BadRequestAlertException("Event Not Found", ENTITY_NAME, "eventNotFound");
    }
}
