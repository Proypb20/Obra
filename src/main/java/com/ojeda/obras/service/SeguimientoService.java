package com.ojeda.obras.service;

import com.ojeda.obras.domain.*;
import com.ojeda.obras.repository.SeguimientoRepository;
import com.ojeda.obras.service.dto.SeguimientoDTO;
import com.ojeda.obras.service.mapper.SeguimientoMapper;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seguimiento}.
 */
@Service
@Transactional
public class SeguimientoService {

    private final Logger log = LoggerFactory.getLogger(SeguimientoService.class);

    private final SeguimientoRepository seguimientoRepository;

    private final SeguimientoMapper seguimientoMapper;

    private final TareaService tareaService;

    private final MovimientoService movimientoService;

    public SeguimientoService(
        SeguimientoRepository seguimientoRepository,
        SeguimientoMapper seguimientoMapper,
        TareaService tareaService,
        MovimientoService movimientoService
    ) {
        this.seguimientoRepository = seguimientoRepository;
        this.seguimientoMapper = seguimientoMapper;
        this.tareaService = tareaService;
        this.movimientoService = movimientoService;
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SeguimientoDTO> findAll() {
        log.debug("Request to get all Seguimiento");
        return seguimientoRepository.findAll().stream().map(seguimientoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Seguimiento> findAllByObraNameAndPeriodNameAndConceptName(String obraName, String periodName, String conceptName) {
        log.debug("Request to get all Seguimiento By Obra, Period y Concepto; {},{},{}", obraName, periodName, conceptName);
        return seguimientoRepository.findAllByObraNameAndPeriodNameAndConceptName(obraName, periodName, conceptName);
    }

    @Transactional(readOnly = true)
    public List<Seguimiento> findAllByObraName(String obraName) {
        log.debug("Request to get all Seguimiento By Obra; {}", obraName);
        return seguimientoRepository.findAllByObraName(obraName);
    }

    @Transactional(readOnly = true)
    public List<Seguimiento> findAllByObraNameAndPeriodName(String obraName, String periodName) {
        log.debug("Request to get all Seguimiento By Obra y Period; {},{}", obraName, periodName);
        return seguimientoRepository.findAllByObraNameAndPeriodName(obraName, periodName);
    }

    /**
     * Generate Excel File
     *
     */
    public File generateFile(String obraName, List<String> periods) throws IOException, URISyntaxException {
        log.debug("GenerateFile Service: {},{},{}", obraName, periods);

        /* Genero el excel */
        Workbook workbook = new XSSFWorkbook();
        String sheetS = "S-" + obraName.replace(" ", "-");

        Sheet sheet = workbook.createSheet(sheetS);

        sheet.setColumnWidth(0, 5000); // Descripcion
        sheet.setColumnWidth(1, 3190); // Saldo MO
        sheet.setColumnWidth(2, 3190); // Presupuesto
        sheet.setColumnWidth(3, 3190); // Total
        int p = 3;
        for (String period : periods) {
            p++;
            sheet.setColumnWidth(p, 3190); // Resumenes
        }

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:C1"));

        /* Titulos */

        Row title = sheet.createRow(0);

        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont fontTitle = ((XSSFWorkbook) workbook).createFont();
        fontTitle.setFontName("Arial");
        fontTitle.setFontHeightInPoints((short) 8);
        fontTitle.setBold(true);
        titleStyle.setFont(fontTitle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle titleStyleDT = workbook.createCellStyle();
        XSSFFont fontTitleDT = ((XSSFWorkbook) workbook).createFont();
        fontTitleDT.setFontName("Arial");
        fontTitleDT.setFontHeightInPoints((short) 8);
        fontTitleDT.setBold(false);
        titleStyleDT.setFont(fontTitleDT);
        titleStyleDT.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle styleMon = workbook.createCellStyle();
        XSSFFont fontSGMon = ((XSSFWorkbook) workbook).createFont();
        fontSGMon.setFontName("Arial");
        fontSGMon.setFontHeightInPoints((short) 8);
        styleMon.setFont(fontSGMon);
        styleMon.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfMon = workbook.createDataFormat();
        styleMon.setDataFormat(dfMon.getFormat("$0.00"));

        CellStyle styleMonB = workbook.createCellStyle();
        XSSFFont fontSGMonB = ((XSSFWorkbook) workbook).createFont();
        fontSGMonB.setFontName("Arial");
        fontSGMonB.setFontHeightInPoints((short) 8);
        fontSGMonB.setBold(true);
        styleMonB.setFont(fontSGMonB);
        styleMonB.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfMonB = workbook.createDataFormat();
        styleMonB.setDataFormat(dfMonB.getFormat("$0.00"));

        Cell titleCell = title.createCell(0);
        titleCell.setCellValue("SEGUIMIENTO " + obraName);
        titleCell.setCellStyle(titleStyle);

        Cell titleCellDT = title.createCell(3);
        titleCellDT.setCellValue("TOTAL");
        titleCellDT.setCellStyle(titleStyle);

        p = 3;
        for (String period : periods) {
            p++;
            titleCellDT = title.createCell(p);
            titleCellDT.setCellValue(period);
            titleCellDT.setCellStyle(titleStyleDT);
        }

        Row title2 = sheet.createRow(1);

        titleCellDT = title2.createCell(0);
        titleCellDT.setCellValue("Descripcion");
        titleCellDT.setCellStyle(titleStyle);

        titleCellDT = title2.createCell(1);
        titleCellDT.setCellValue("Saldo MO");
        titleCellDT.setCellStyle(titleStyle);

        titleCellDT = title2.createCell(2);
        titleCellDT.setCellValue("PRESUPUESTO");
        titleCellDT.setCellStyle(titleStyle);

        titleCellDT = title2.createCell(3);
        titleCellDT.setCellValue("PAGOS");
        titleCellDT.setCellStyle(titleStyle);

        p = 3;
        int r = 0;
        for (String period : periods) {
            p++;
            r++;
            titleCellDT = title2.createCell(p);
            titleCellDT.setCellValue(r + "° RESUMEN");
            titleCellDT.setCellStyle(titleStyle);
        }

        /* Obtengo Lista de conceptos */

        List<Tarea> tareas = tareaService.findAllByObraName(obraName);
        List<String> conceptos = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (!conceptos.contains(tarea.getConcepto().getName())) {
                conceptos.add(tarea.getConcepto().getName());
            }
        }
        List<Seguimiento> segs = findAllByObraName(obraName);
        for (Seguimiento seg : segs) {
            if (!conceptos.contains(seg.getConceptName())) {
                conceptos.add(seg.getConceptName());
            }
        }

        Double totalSaldo = 0D;
        Double totalPresupuesto = 0D;
        Double totalPagos = 0D;

        /*Recorro los conceptos y obtengo los valores*/

        Row conceptoR;

        int ro;
        ro = 1;
        for (String concepto : conceptos) {
            ro++;
            conceptoR = sheet.createRow(ro);

            titleCellDT = conceptoR.createCell(0);
            titleCellDT.setCellValue(concepto);
            titleCellDT.setCellStyle(titleStyleDT);

            // Obtengo el presupuesto

            Double presupuesto;
            presupuesto = 0D;
            for (Tarea tarea : tareas) {
                if (tarea.getConcepto().getName().equals(concepto)) {
                    presupuesto = presupuesto + tarea.getCost();
                }
            }

            titleCellDT = conceptoR.createCell(2);
            titleCellDT.setCellValue(presupuesto);
            titleCellDT.setCellStyle(styleMonB);

            // Obtengo los pagos
            Double amount, totalAmount;
            totalAmount = 0D;
            p = 3;
            for (String period : periods) {
                p++;
                amount = 0D;

                List<Seguimiento> pagos = findAllByObraNameAndPeriodNameAndConceptName(
                    obraName,
                    period.substring(0, 1).toUpperCase() + period.substring(1),
                    concepto
                );
                log.debug("Pagos: {}", pagos);

                if (pagos.size() > 0) {
                    for (Seguimiento seg : pagos) {
                        amount = amount + (seg.getAmount());
                    }
                }
                log.debug("Monto: {}", amount);

                titleCellDT = conceptoR.createCell(p);
                titleCellDT.setCellValue(amount);
                titleCellDT.setCellStyle(styleMon);

                totalAmount = totalAmount + amount;
            }

            titleCellDT = conceptoR.createCell(3);
            titleCellDT.setCellValue(totalAmount);
            titleCellDT.setCellStyle(styleMon);

            titleCellDT = conceptoR.createCell(1);
            titleCellDT.setCellValue((presupuesto - totalAmount));
            titleCellDT.setCellStyle(styleMonB);

            totalSaldo = totalSaldo + (presupuesto - totalAmount);
            totalPresupuesto = totalPresupuesto + presupuesto;
            totalPagos = totalPagos + totalAmount;
        }

        ro++;
        Row conceptoTotal = sheet.createRow(ro);

        titleCellDT = conceptoTotal.createCell(0);
        titleCellDT.setCellValue("TOTAL");
        titleCellDT.setCellStyle(titleStyle);

        titleCellDT = conceptoTotal.createCell(1);
        titleCellDT.setCellValue(totalSaldo);
        titleCellDT.setCellStyle(styleMonB);

        titleCellDT = conceptoTotal.createCell(2);
        titleCellDT.setCellValue(totalPresupuesto);
        titleCellDT.setCellStyle(styleMonB);

        titleCellDT = conceptoTotal.createCell(3);
        titleCellDT.setCellValue(totalPagos);
        titleCellDT.setCellStyle(styleMonB);

        p = 3;
        Double totPeriodo;
        for (String period : periods) {
            totPeriodo = 0D;
            p++;
            List<Seguimiento> totPeriodos = findAllByObraNameAndPeriodName(
                obraName,
                period.substring(0, 1).toUpperCase() + period.substring(1)
            );
            for (Seguimiento seg : totPeriodos) {
                totPeriodo = totPeriodo + seg.getAmount();
            }
            titleCellDT = conceptoTotal.createCell(p);
            titleCellDT.setCellValue(totPeriodo);
            titleCellDT.setCellStyle(styleMonB);
        }

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }
}
