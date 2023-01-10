package com.ojeda.obras.web.rest;

import com.ojeda.obras.domain.ResObra;
import com.ojeda.obras.domain.Seguimiento;
import com.ojeda.obras.repository.SeguimientoRepository;
import com.ojeda.obras.service.ResObraQueryService;
import com.ojeda.obras.service.SeguimientoQueryService;
import com.ojeda.obras.service.SeguimientoService;
import com.ojeda.obras.service.criteria.ResObraCriteria;
import com.ojeda.obras.service.criteria.SeguimientoCriteria;
import com.ojeda.obras.service.dto.ResObraDTO;
import com.ojeda.obras.service.dto.SeguimientoDTO;
import com.ojeda.obras.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Seguimiento}.
 */
@RestController
@RequestMapping("/api")
public class SeguimientoResource {

    private final Logger log = LoggerFactory.getLogger(SeguimientoResource.class);

    private static final String ENTITY_NAME = "Seguimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeguimientoService seguimientoService;

    private final SeguimientoRepository seguimientoRepository;

    private final SeguimientoQueryService seguimientoQueryService;

    private final ResObraQueryService resObraQueryService;

    public SeguimientoResource(
        SeguimientoService seguimientoService,
        SeguimientoRepository seguimientoRepository,
        SeguimientoQueryService seguimientoQueryService,
        ResObraQueryService resObraQueryService
    ) {
        this.seguimientoService = seguimientoService;
        this.seguimientoRepository = seguimientoRepository;
        this.seguimientoQueryService = seguimientoQueryService;
        this.resObraQueryService = resObraQueryService;
    }

    /**
     * {@code GET  /Seguimientos} : get all the Seguimientos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Seguimientos in body.
     */
    @GetMapping("/seguimiento-rep")
    public ResponseEntity<List<SeguimientoDTO>> getAllSeguimientos(SeguimientoCriteria criteria) {
        log.debug("REST request to get Seguimientos by criteria: {}", criteria);
        List<SeguimientoDTO> entityList = seguimientoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /seguimiento-rep/exportXML"} : getAllSeguimientosReport
     *
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping(value = "/seguimiento-rep/exportXML", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateFile(ResObraCriteria criteria, HttpServletResponse response)
        throws IOException, URISyntaxException {
        log.debug("REST request to get ResObras and Export by criteria: {}", criteria);
        List<ResObraDTO> resObras = resObraQueryService.findByCriteria(criteria);
        List<String> periods = new ArrayList();
        List<LocalDate> periodsD = new ArrayList<>();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd-MMM-yy")
            .toFormatter(Locale.ENGLISH);

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy");

        for (ResObraDTO resObra : resObras) {
            //periods.add(resObra.getPeriodName());
            LocalDate dt = LocalDate.parse(
                "01-" + resObra.getPeriodName().replace("Ene", "Jan").replace("Abr", "Apr").replace("Ago", "Aug").replace("Dic", "Dec"),
                formatter
            );
            log.debug("Fecha: {}", dt);
            periodsD.add(dt);
        }

        /*for (ResObraDTO resObra : resObras ){
            periodsD.add(LocalDate.parse(resObra.getPeriodName(),formatter));
        }*/

        Collections.sort(periodsD);

        // periodsD.stream().collect(Collectors.toCollection(HashSet::new));

        /*periods.sort(Comparator.comparing(s -> LocalDate.parse("01-" + s
                .replace("Ene","Jan")
                .replace("Abr","Apr")
                .replace("Ago","Aug")
                .replace("Dic","Dec")
            , formatter)));*/

        log.debug("Fechas Ordenadas: {}", periodsD);
        for (LocalDate ld : periodsD) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM-yy");
            String formattedString = ld
                .format(formatter2)
                .replace("Jan", "Ene")
                .replace("Apr", "Abr")
                .replace("Aug", "Ago")
                .replace("Dec", "Dic")
                .replace(".", "");

            log.debug("Periodo Formateado: {}", formattedString);
            if (!periods.contains(formattedString)) {
                periods.add(formattedString);
                log.debug("Agregado");
            } else {
                log.debug("Ya Existe");
            }
        }

        log.debug("Periodos: {}", periods);

        if (periods.size() != 0) {
            File file = seguimientoService.generateFile(resObras.get(0).getObraName(), periods);
            response.setHeader("Content-Disposition", "attachment; filename=".concat(file.getName()));
            return ResponseEntity.ok().body(Files.readAllBytes(file.toPath()));
        } else throw new BadRequestAlertException("No se han encontrado Movimientos", ENTITY_NAME, "No se puede crear el archivo");
    }
}
