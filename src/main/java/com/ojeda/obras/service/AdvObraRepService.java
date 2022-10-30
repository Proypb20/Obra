package com.ojeda.obras.service;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.repository.AdvObraRepRepository;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import com.ojeda.obras.service.mapper.AdvObraRepMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdvObraRep}.
 */
@Service
@Transactional
public class AdvObraRepService {

    private final Logger log = LoggerFactory.getLogger(AdvObraRepService.class);

    private final AdvObraRepRepository advObraRepRepository;

    private final AdvObraRepMapper advObraRepMapper;

    public AdvObraRepService(AdvObraRepRepository advObraRepRepository, AdvObraRepMapper advObraRepMapper) {
        this.advObraRepRepository = advObraRepRepository;
        this.advObraRepMapper = advObraRepMapper;
    }

    /**
     * Get all the conceptos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvObraRepDTO> findAll() {
        log.debug("Request to get all advObraReps");
        return advObraRepRepository.findAll().stream().map(advObraRepMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the conceptos by ObraId.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvObraRep> findAllByObraId(Long id) {
        log.debug("Request to get all advObraReps");
        return advObraRepRepository.findAllByObraId(id);
    }

    public File generateFile(List<AdvObraRep> advObraReps) throws IOException, URISyntaxException {
        log.debug("Generate File Service");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("AVANCE");
        sheet.setColumnWidth(0, 2140);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 17200);
        sheet.setColumnWidth(3, 3200);
        sheet.setColumnWidth(4, 3200);
        sheet.setColumnWidth(5, 3200);
        sheet.setColumnWidth(6, 3200);
        sheet.setColumnWidth(7, 3200);
        sheet.setColumnWidth(8, 3200);

        /* Titulos */
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("ID TAREA");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("CONCEPTO");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("TAREA");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("CANTIDAD");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("COSTO MO");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("% TAREA");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("AVANCE");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("ACUMULADO");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        int i = 0;
        Double sumQty = 0D, sumCost = 0D, sumPorcTarea = 0D, sumAdv = 0D, sumAcum = 0D;
        for (AdvObraRep advObraRep : advObraReps) {
            i++;
            Row row = sheet.createRow(i);

            Cell cell = row.createCell(0);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getId());

            cell = row.createCell(1);
            style.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getConcepto());

            cell = row.createCell(2);
            style.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getTaskName());

            cell = row.createCell(3);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getQuantity());

            cell = row.createCell(4);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getCost());

            cell = row.createCell(5);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getPorcTarea());

            cell = row.createCell(6);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getAdvanceStatus());

            cell = row.createCell(7);
            style.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(style);
            cell.setCellValue(advObraRep.getPorcAdv());

            sumQty = sumQty + advObraRep.getQuantity();
            sumCost = sumCost + advObraRep.getCost();
            sumPorcTarea = sumPorcTarea + advObraRep.getPorcTarea();
            sumAdv = sumAdv + advObraRep.getAdvanceStatus();
            sumAcum = advObraRep.getPorcAdv();
        }

        CellStyle styleT = workbook.createCellStyle();
        styleT.setWrapText(true);
        styleT.setBorderRight(BorderStyle.THIN);
        styleT.setBorderLeft(BorderStyle.THIN);
        styleT.setBorderBottom(BorderStyle.THIN);
        styleT.setBorderTop(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        styleT.setFont(font);
        styleT.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleT.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row row = sheet.createRow(i + 1);
        Cell cell = row.createCell(2);
        cell.setCellValue("Totales:");
        cell.setCellStyle(styleT);

        cell = row.createCell(3);
        cell.setCellValue(sumQty);
        styleT.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(styleT);

        cell = row.createCell(4);
        cell.setCellValue(sumCost);
        styleT.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(styleT);

        cell = row.createCell(5);
        cell.setCellValue(sumPorcTarea);
        cell.setCellStyle(styleT);

        cell = row.createCell(6);
        cell.setCellValue((sumAdv / i));
        cell.setCellStyle(styleT);

        cell = row.createCell(7);
        cell.setCellValue(sumAcum);
        cell.setCellStyle(styleT);

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }
}
