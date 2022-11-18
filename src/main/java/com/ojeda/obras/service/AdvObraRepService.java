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
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        CellStyle styleId = workbook.createCellStyle();
        styleId.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle stylePorc = workbook.createCellStyle();
        stylePorc.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfPorc = workbook.createDataFormat();
        stylePorc.setDataFormat(dfPorc.getFormat("0.00%"));

        CellStyle styleNum = workbook.createCellStyle();
        styleNum.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfDec = workbook.createDataFormat();
        styleNum.setDataFormat(dfDec.getFormat("0.00"));

        CellStyle styleMon = workbook.createCellStyle();
        styleMon.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfMon = workbook.createDataFormat();
        styleMon.setDataFormat(dfMon.getFormat("$0.00"));

        CellStyle styleText = workbook.createCellStyle();
        styleText.setAlignment(HorizontalAlignment.LEFT);

        int i = 0;
        Double sumQty = 0D, sumCost = 0D, sumPorcTarea = 0D, sumAdv = 0D, sumAcum = 0D;
        for (AdvObraRep advObraRep : advObraReps) {
            i++;
            Row row = sheet.createRow(i);

            Cell cellId = row.createCell(0);
            cellId.setCellStyle(styleId);
            cellId.setCellValue(advObraRep.getId());

            Cell cellConc = row.createCell(1);
            cellConc.setCellStyle(styleText);
            cellConc.setCellValue(advObraRep.getConcepto());

            Cell cellTask = row.createCell(2);
            cellTask.setCellStyle(styleText);
            cellTask.setCellValue(advObraRep.getTaskName());

            Cell cellQty = row.createCell(3);
            cellQty.setCellStyle(styleNum);
            cellQty.setCellValue(advObraRep.getQuantity());

            Cell cellCost = row.createCell(4);
            cellCost.setCellStyle(styleMon);
            cellCost.setCellValue(advObraRep.getCost());

            Cell cellPorcTarea = row.createCell(5);
            cellPorcTarea.setCellStyle(stylePorc);
            cellPorcTarea.setCellValue((advObraRep.getPorcTarea() / 100));

            Cell cellAdv = row.createCell(6);
            cellAdv.setCellStyle(stylePorc);
            cellAdv.setCellValue((advObraRep.getAdvanceStatus() / 100));

            Cell cellPorcAdv = row.createCell(7);
            cellPorcAdv.setCellStyle(stylePorc);
            cellPorcAdv.setCellValue((advObraRep.getPorcAdv() / 100));

            sumQty = sumQty + advObraRep.getQuantity();
            sumCost = sumCost + advObraRep.getCost();
            sumPorcTarea = sumPorcTarea + advObraRep.getPorcTarea();
            sumAdv = sumAdv + advObraRep.getAdvanceStatus();
            sumAcum = advObraRep.getPorcAdv();
        }

        CellStyle styleTNum = workbook.createCellStyle();
        styleTNum.setWrapText(true);
        styleTNum.setBorderRight(BorderStyle.THIN);
        styleTNum.setBorderLeft(BorderStyle.THIN);
        styleTNum.setBorderBottom(BorderStyle.THIN);
        styleTNum.setBorderTop(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        styleTNum.setFont(font);
        styleTNum.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleTNum.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTNum.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle styleTText = workbook.createCellStyle();
        styleTText.setWrapText(true);
        styleTText.setBorderRight(BorderStyle.THIN);
        styleTText.setBorderLeft(BorderStyle.THIN);
        styleTText.setBorderBottom(BorderStyle.THIN);
        styleTText.setBorderTop(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        styleTText.setFont(font);
        styleTText.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleTText.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTText.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleTMon = workbook.createCellStyle();
        styleTMon.setWrapText(true);
        styleTMon.setBorderRight(BorderStyle.THIN);
        styleTMon.setBorderLeft(BorderStyle.THIN);
        styleTMon.setBorderBottom(BorderStyle.THIN);
        styleTMon.setBorderTop(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        styleTMon.setFont(font);
        styleTMon.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleTMon.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTMon.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfTMon = workbook.createDataFormat();
        styleTMon.setDataFormat(dfTMon.getFormat("$0.00"));

        CellStyle styleTPorc = workbook.createCellStyle();
        styleTPorc.setWrapText(true);
        styleTPorc.setBorderRight(BorderStyle.THIN);
        styleTPorc.setBorderLeft(BorderStyle.THIN);
        styleTPorc.setBorderBottom(BorderStyle.THIN);
        styleTPorc.setBorderTop(BorderStyle.THIN);
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        styleTPorc.setFont(font);
        styleTPorc.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleTPorc.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTPorc.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfTPorc = workbook.createDataFormat();
        styleTPorc.setDataFormat(dfTPorc.getFormat("0.00%"));

        Row row = sheet.createRow(i + 1);
        Cell cellTT = row.createCell(2);
        cellTT.setCellValue("Totales:");
        cellTT.setCellStyle(styleTText);

        Cell cellTQty = row.createCell(3);
        cellTQty.setCellValue(sumQty);
        cellTQty.setCellStyle(styleTNum);

        Cell cellTCost = row.createCell(4);
        cellTCost.setCellValue(sumCost);
        cellTCost.setCellStyle(styleTMon);

        Cell cellTPorc = row.createCell(5);
        cellTPorc.setCellValue((sumPorcTarea / 100));
        cellTPorc.setCellStyle(styleTPorc);

        Cell cellTAdv = row.createCell(6);
        cellTAdv.setCellValue(((sumAdv / i) / 100));
        cellTAdv.setCellStyle(styleTPorc);

        Cell cellTAcum = row.createCell(7);
        cellTAcum.setCellValue((sumAcum / 100));
        cellTAcum.setCellStyle(styleTPorc);

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }
}
