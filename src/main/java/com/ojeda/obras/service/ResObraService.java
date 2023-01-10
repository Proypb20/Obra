package com.ojeda.obras.service;

import com.ojeda.obras.domain.ResObra;
import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.repository.ResObraRepository;
import com.ojeda.obras.service.dto.ResObraDTO;
import com.ojeda.obras.service.mapper.ResObraMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
 * Service Implementation for managing {@link ResObra}.
 */
@Service
@Transactional
public class ResObraService {

    private final Logger log = LoggerFactory.getLogger(ResObraService.class);

    private final ResObraRepository resObraRepository;

    private final ResObraMapper resObraMapper;

    public ResObraService(ResObraRepository resObraRepository, ResObraMapper resObraMapper) {
        this.resObraRepository = resObraRepository;
        this.resObraMapper = resObraMapper;
    }

    /**
     * Get all the subcoPayConcept.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ResObraDTO> findAll() {
        log.debug("Request to get all ResObra");
        return resObraRepository.findAll().stream().map(resObraMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Generate Excel File
     *
     */
    public File generateFile(List<ResObraDTO> resObras, String obraName, String periodName) throws IOException, URISyntaxException {
        log.debug("GenerateFile Service: {},{},{}", resObras, obraName, periodName);

        /* Genero el excel */
        Workbook workbook = new XSSFWorkbook();
        String sheetS = "S-" + obraName.replace(" ", "-");

        Sheet sheet = workbook.createSheet(sheetS);

        sheet.setColumnWidth(0, 4200);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 3300);
        sheet.setColumnWidth(5, 3300);

        sheet.addMergedRegion(CellRangeAddress.valueOf("C2:D2"));

        /* Titulos */

        Row title = sheet.createRow(1);
        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont fontTitle = ((XSSFWorkbook) workbook).createFont();
        fontTitle.setFontName("Arial");
        fontTitle.setFontHeightInPoints((short) 10);
        fontTitle.setBold(true);
        titleStyle.setFont(fontTitle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle titleStyleDT = workbook.createCellStyle();
        XSSFFont fontTitleDT = ((XSSFWorkbook) workbook).createFont();
        fontTitleDT.setFontName("Arial");
        fontTitleDT.setFontHeightInPoints((short) 10);
        fontTitleDT.setBold(false);
        titleStyleDT.setFont(fontTitleDT);
        titleStyleDT.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle titleStyleD = workbook.createCellStyle();
        XSSFFont fontTitleD = ((XSSFWorkbook) workbook).createFont();
        fontTitleD.setFontName("Arial");
        fontTitleD.setFontHeightInPoints((short) 10);
        fontTitleD.setBold(false);
        titleStyleD.setFont(fontTitleD);
        DataFormat dfDateD = workbook.createDataFormat();
        titleStyleD.setDataFormat(dfDateD.getFormat("DD/M/YYYY"));
        titleStyleD.setAlignment(HorizontalAlignment.RIGHT);

        Cell titleCell = title.createCell(2);
        titleCell.setCellValue("RESUMEN OBRA " + obraName);
        titleCell.setCellStyle(titleStyle);

        Cell titleCellDT = title.createCell(4);
        titleCellDT.setCellValue("Fecha");
        titleCellDT.setCellStyle(titleStyleDT);

        Cell titleCellD = title.createCell(5);
        titleCellD.setCellValue(new Date());
        titleCellD.setCellStyle(titleStyleD);

        Row header = sheet.createRow(4);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Cell headerCell = null;

        headerCell = header.createCell(0);
        headerCell.setCellValue("Fecha");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Origen");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Referencia");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("TOTAL");
        headerCell.setCellStyle(headerStyle);

        /* Linea Saldo General */

        Row rowSG = sheet.createRow(5);
        CellStyle rowSGStyle = workbook.createCellStyle();
        XSSFFont fontSG = ((XSSFWorkbook) workbook).createFont();
        fontSG.setFontName("Arial");
        fontSG.setFontHeightInPoints((short) 10);
        fontSG.setBold(true);
        rowSGStyle.setFont(fontSG);

        Cell cell = rowSG.createCell(3);
        cell.setCellValue("SALDO GENERAL DE LA OBRA");
        cell.setCellStyle(rowSGStyle);

        Double sumTotalI = 0D, sumTotalE = 0D;

        for (ResObraDTO resObra : resObras) {
            if (resObra.getType().equals("I")) {
                sumTotalI = sumTotalI + resObra.getAmount();
            } else {
                sumTotalE = sumTotalE + resObra.getAmount();
            }
        }

        cell = rowSG.createCell(5);

        CellStyle styleMon = workbook.createCellStyle();
        XSSFFont fontSGMon = ((XSSFWorkbook) workbook).createFont();
        fontSGMon.setFontName("Arial");
        fontSGMon.setFontHeightInPoints((short) 10);
        fontSGMon.setBold(true);
        styleMon.setFont(fontSGMon);
        styleMon.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfMon = workbook.createDataFormat();
        styleMon.setDataFormat(dfMon.getFormat("$0.00"));
        cell.setCellValue(sumTotalI - sumTotalE);
        cell.setCellStyle(styleMon);

        /* Linea total Ingresos */

        Row rowTI = sheet.createRow(6);

        cell = rowTI.createCell(3);

        CellStyle styleTI = workbook.createCellStyle();
        XSSFFont fontTI = ((XSSFWorkbook) workbook).createFont();
        fontTI.setFontName("Arial");
        fontTI.setFontHeightInPoints((short) 10);
        fontTI.setBold(false);
        styleTI.setFont(fontTI);
        styleTI.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellValue("TOTAL INGRESOS");
        cell.setCellStyle(styleTI);

        cell = rowTI.createCell(5);

        CellStyle styleTIA = workbook.createCellStyle();
        XSSFFont fontTIA = ((XSSFWorkbook) workbook).createFont();
        fontTIA.setFontName("Arial");
        fontTIA.setFontHeightInPoints((short) 10);
        fontTIA.setBold(false);
        styleTIA.setFont(fontTIA);
        styleTIA.setAlignment(HorizontalAlignment.RIGHT);
        styleTIA.setDataFormat(dfMon.getFormat("$0.00"));
        cell.setCellValue(sumTotalI);
        cell.setCellStyle(styleTIA);

        /* Lineas de Ingresos */

        Row rowI = null;
        int rowid = 6;

        XSSFFont fontI = ((XSSFWorkbook) workbook).createFont();
        fontI.setFontName("Arial");
        fontI.setFontHeightInPoints((short) 10);
        fontI.setBold(false);

        CellStyle styleID = workbook.createCellStyle();
        styleID.setFont(fontI);
        /* CreationHelper createHelper = workbook.getCreationHelper();
        styleID.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));*/
        DataFormat dfDate = workbook.createDataFormat();
        styleID.setDataFormat(dfDate.getFormat("DD/M/YYYY"));
        styleID.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle styleIS = workbook.createCellStyle();
        styleIS.setFont(fontI);
        styleIS.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleIA = workbook.createCellStyle();
        styleIA.setFont(fontI);
        styleIA.setAlignment(HorizontalAlignment.RIGHT);
        styleIA.setDataFormat(dfMon.getFormat("$0.00"));

        for (ResObraDTO resObra : resObras) {
            if (resObra.getType().equals("I")) {
                rowid = rowid + 1;
                rowI = sheet.createRow(rowid);

                cell = rowI.createCell(0);
                cell.setCellValue(resObra.getDate());
                cell.setCellStyle(styleID);

                cell = rowI.createCell(1);
                cell.setCellValue(resObra.getSource());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(2);
                cell.setCellValue(resObra.getReference());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(3);
                cell.setCellValue(resObra.getDescription());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(4);
                cell.setCellValue(resObra.getAmount());
                cell.setCellStyle(styleIA);
            }
        }

        /* Linea total egresos */

        rowid = rowid + 1;

        Row rowTE = sheet.createRow(rowid);

        cell = rowTE.createCell(3);

        CellStyle styleTE = workbook.createCellStyle();
        XSSFFont fontTE = ((XSSFWorkbook) workbook).createFont();
        fontTE.setFontName("Arial");
        fontTE.setFontHeightInPoints((short) 10);
        fontTE.setBold(false);
        styleTE.setFont(fontTE);
        styleTE.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellValue("TOTAL EGRESOS");
        cell.setCellStyle(styleTE);

        cell = rowTE.createCell(5);

        CellStyle styleTEA = workbook.createCellStyle();
        XSSFFont fontTEA = ((XSSFWorkbook) workbook).createFont();
        fontTEA.setFontName("Arial");
        fontTEA.setFontHeightInPoints((short) 10);
        fontTEA.setBold(false);
        styleTEA.setFont(fontTIA);
        styleTEA.setAlignment(HorizontalAlignment.RIGHT);
        styleTEA.setDataFormat(dfMon.getFormat("$0.00"));
        cell.setCellValue(sumTotalE);
        cell.setCellStyle(styleTEA);

        /* Lineas Egresos */

        for (ResObraDTO resObra : resObras) {
            if (resObra.getType().equals("E")) {
                rowid = rowid + 1;
                rowI = sheet.createRow(rowid);

                cell = rowI.createCell(0);
                cell.setCellValue(resObra.getDate());
                cell.setCellStyle(styleID);

                cell = rowI.createCell(1);
                cell.setCellValue(resObra.getSource());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(2);
                cell.setCellValue(resObra.getReference());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(3);
                cell.setCellValue(resObra.getDescription());
                cell.setCellStyle(styleIS);

                cell = rowI.createCell(4);
                cell.setCellValue(resObra.getAmount());
                cell.setCellStyle(styleIA);
            }
        }

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }
}
