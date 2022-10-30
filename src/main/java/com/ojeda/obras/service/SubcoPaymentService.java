package com.ojeda.obras.service;

import com.ojeda.obras.domain.SubcoPayAmount;
import com.ojeda.obras.domain.SubcoPayConcept;
import com.ojeda.obras.domain.SubcoPayPayment;
import com.ojeda.obras.domain.SubcoPaySubco;
import com.ojeda.obras.repository.SubcoPayAmountRepository;
import com.ojeda.obras.repository.SubcoPayConceptRepository;
import com.ojeda.obras.repository.SubcoPayPaymentRepository;
import com.ojeda.obras.repository.SubcoPaySubcoRepository;
import com.ojeda.obras.service.dto.SubcoPayAmountDTO;
import com.ojeda.obras.service.dto.SubcoPayConceptDTO;
import com.ojeda.obras.service.dto.SubcoPayPaymentDTO;
import com.ojeda.obras.service.dto.SubcoPaySubcoDTO;
import com.ojeda.obras.service.mapper.SubcoPayPaymentMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubcoPayPayment}.
 */
@Service
@Transactional
public class SubcoPaymentService {

    private final Logger log = LoggerFactory.getLogger(SubcoPaymentService.class);

    private final SubcoPayConceptRepository subcoPayConceptRepository;
    private final SubcoPayAmountRepository subcoPayAmountRepository;
    private final SubcoPayPaymentRepository subcoPayPaymentRepository;
    private final SubcoPaySubcoRepository subcoPaySubcoRepository;

    private final SubcoPayPaymentMapper subcoPayPaymentMapper;

    public SubcoPaymentService(
        SubcoPayConceptRepository subcoPayConceptRepository,
        SubcoPayAmountRepository subcoPayAmountRepository,
        SubcoPayPaymentRepository subcoPayPaymentRepository,
        SubcoPaySubcoRepository subcoPaySubcoRepository,
        SubcoPayPaymentMapper subcoPayPaymentMapper
    ) {
        this.subcoPayConceptRepository = subcoPayConceptRepository;
        this.subcoPayAmountRepository = subcoPayAmountRepository;
        this.subcoPayPaymentRepository = subcoPayPaymentRepository;
        this.subcoPaySubcoRepository = subcoPaySubcoRepository;
        this.subcoPayPaymentMapper = subcoPayPaymentMapper;
    }

    public File generateFile(
        List<SubcoPayConceptDTO> subcoPayConcepts,
        List<SubcoPayAmountDTO> subcoPayAmounts,
        List<SubcoPayPaymentDTO> subcoPayPayments,
        List<SubcoPaySubcoDTO> subcoPaySubcos
    ) throws IOException, URISyntaxException {
        log.debug("Generate File Service");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PAGOS SUBCONTRATISTAS");

        sheet.setColumnWidth(0, 4500);
        for (int i = 1; i <= subcoPaySubcos.size(); i++) {
            sheet.setColumnWidth(i, 4500);
        }

        Row header1 = sheet.createRow(0);
        Row header2 = sheet.createRow(1);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell header1Cell = header1.createCell(0);
        header1Cell.setCellValue("CONTRATISTA");
        header1Cell.setCellStyle(headerStyle);

        Cell header2Cell = header2.createCell(0);
        header2Cell.setCellValue("OBRA");
        header2Cell.setCellStyle(headerStyle);

        int i = 0;
        for (SubcoPaySubcoDTO subcoPaySubcoDTO : subcoPaySubcos) {
            i++;
            header1Cell = header1.createCell(i);
            header1Cell.setCellValue(subcoPaySubcoDTO.getSubcontratista());
            header1Cell.setCellStyle(headerStyle);

            header2Cell = header2.createCell(i);
            header2Cell.setCellValue(subcoPaySubcoDTO.getObraName());
            header2Cell.setCellStyle(headerStyle);
        }

        /* Lineas */
        int j = 1;

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

        for (SubcoPayConceptDTO subcoPayConceptDTO : subcoPayConcepts) {
            j++;
            Row row = sheet.createRow(j);

            Cell cell = row.createCell(0);
            style.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(style);
            cell.setCellValue(subcoPayConceptDTO.getConceptoName());

            for (int k = 1; k <= subcoPaySubcos.size(); k++) {
                for (SubcoPayAmountDTO subcoPayAmountDTO : subcoPayAmounts) {
                    if (
                        subcoPayAmountDTO.getSubcontratista().equals(header1.getCell(k).getStringCellValue()) &&
                        subcoPayAmountDTO.getObraName().equals(header2.getCell(k).getStringCellValue()) &&
                        subcoPayAmountDTO.getConceptoId().equals(subcoPayConceptDTO.getConceptoId())
                    ) {
                        cell = row.createCell(k);
                        style.setAlignment(HorizontalAlignment.RIGHT);
                        cell.setCellStyle(style);
                        cell.setCellValue(subcoPayAmountDTO.getCost());
                    }
                }
            }
        }

        j++;

        Row row = sheet.createRow(j);

        Cell cell = row.createCell(0);
        style.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(style);
        cell.setCellValue("PAGOS");

        for (int k = 1; k <= subcoPaySubcos.size(); k++) {
            for (SubcoPayPaymentDTO subcoPayPaymentDTO : subcoPayPayments) {
                if (
                    subcoPayPaymentDTO.getSubcontratista().equals(header1.getCell(k).getStringCellValue()) &&
                    subcoPayPaymentDTO.getObraName().equals(header2.getCell(k).getStringCellValue())
                ) {
                    cell = row.createCell(k);
                    style.setAlignment(HorizontalAlignment.RIGHT);
                    cell.setCellStyle(style);
                    cell.setCellValue(subcoPayPaymentDTO.getAmount());
                }
            }
        }

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }

    /**
     * Generate Excel File
     *
     */
    public File generateFile(List<SubcoPayConceptDTO> subcoPayConcepts, List<SubcoPaySubcoDTO> subcoPaySubcos)
        throws IOException, URISyntaxException {
        log.debug("GenerateFile Service");

        int filaTotal, filaPagado, filaSaldo, filaPorcPagado;

        log.debug("Generando Arrays");
        String[][] tabla = new String[6 + subcoPayConcepts.size()][1 + subcoPaySubcos.size()];
        tabla[0][0] = "Subcontratistas";
        tabla[1][0] = "Obra";
        int j = 0;

        /* Lleno los titulos */
        for (SubcoPaySubcoDTO subcoPaySubco : subcoPaySubcos) {
            j++;
            tabla[0][j] = subcoPaySubco.getSubcontratista();
            tabla[1][j] = subcoPaySubco.getObraName();
        }
        /* Lleno los conceptos*/
        int i = 1;
        for (SubcoPayConceptDTO subcoPayConcept : subcoPayConcepts) {
            i++;
            tabla[i][0] = subcoPayConcept.getConceptoName();
        }

        /* lleno los titulos de Suma */
        i++;
        tabla[i][0] = "Total";
        filaTotal = i;
        i++;
        tabla[i][0] = "Pagos";
        filaPagado = i;
        i++;
        tabla[i][0] = "Saldo";
        filaSaldo = i;
        i++;
        tabla[i][0] = "Pagado";
        filaPorcPagado = i;

        /* Completo la matriz */
        Double sumTotal;
        for (int col = 1; col <= subcoPaySubcos.size(); col++) {
            sumTotal = 0D;
            for (int fila = 2; fila <= subcoPayConcepts.size() + 2; fila++) {
                Optional<SubcoPayAmount> subcoPayAmount = subcoPayAmountRepository.findBySubcontratistaAndObraNameAndConceptoName(
                    tabla[0][col],
                    tabla[1][col],
                    tabla[fila][0]
                );
                if (subcoPayAmount.isPresent()) {
                    tabla[fila][col] = subcoPayAmount.get().getCost().toString();
                    Optional<SubcoPayConcept> subcoPayConcept = subcoPayConceptRepository.findByConceptoName(tabla[fila][0]);
                    if (subcoPayConcept.isPresent()) {
                        log.debug("Concepto: {} {} ", subcoPayConcept.get().getConceptoName(), subcoPayConcept.get().getSign());
                        if (subcoPayConcept.get().getSign().equals("+")) {
                            sumTotal = sumTotal + subcoPayAmount.get().getCost();
                        } else {
                            sumTotal = sumTotal - subcoPayAmount.get().getCost();
                        }
                    } else {
                        sumTotal = sumTotal + subcoPayAmount.get().getCost();
                    }
                    log.debug("Subtotal: {}", sumTotal);
                } else {
                    tabla[fila][col] = "0";
                }
            }
            tabla[filaTotal][col] = sumTotal.toString();

            Optional<SubcoPayPayment> subcoPayPayment = subcoPayPaymentRepository.findBySubcontratistaAndObraName(
                tabla[0][col],
                tabla[1][col]
            );
            if (subcoPayPayment.isPresent()) {
                tabla[filaPagado][col] = subcoPayPayment.get().getAmount().toString();
                tabla[filaSaldo][col] = String.valueOf(sumTotal - subcoPayPayment.get().getAmount());
                tabla[filaPorcPagado][col] = String.valueOf(subcoPayPayment.get().getAmount() / sumTotal);
            } else {
                tabla[filaPagado][col] = "0";
                tabla[filaSaldo][col] = sumTotal.toString();
                tabla[filaPorcPagado][col] = "0";
            }
        }

        /* Genero el excel */
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PAGOS SUBCONTRATISTAS");

        sheet.setColumnWidth(0, 4500);
        for (int cantCol = 0; cantCol < 1 + subcoPaySubcos.size(); cantCol++) {
            sheet.setColumnWidth(cantCol, 4500);
        }

        for (int cantLin = 0; cantLin < 6 + subcoPayConcepts.size(); cantLin++) {
            Row row = sheet.createRow(cantLin);
            for (int cantCol = 0; cantCol < 1 + subcoPaySubcos.size(); cantCol++) {
                if (cantLin <= 1 || cantCol == 0) {
                    CellStyle styleS = workbook.createCellStyle();
                    styleS.setAlignment(HorizontalAlignment.LEFT);
                    Cell cellS = row.createCell(cantCol);
                    cellS.setCellValue(tabla[cantLin][cantCol]);
                    cellS.setCellStyle(styleS);
                } else {
                    if (filaPorcPagado == cantLin) {
                        CellStyle styleP = workbook.createCellStyle();
                        styleP.setAlignment(HorizontalAlignment.RIGHT);
                        Cell cellP = row.createCell(cantCol);
                        DataFormat df = workbook.createDataFormat();
                        styleP.setDataFormat(df.getFormat("0.00%"));
                        cellP.setCellValue(Double.valueOf(tabla[cantLin][cantCol]));
                        cellP.setCellStyle(styleP);
                    } else {
                        CellStyle styleD = workbook.createCellStyle();
                        styleD.setAlignment(HorizontalAlignment.RIGHT);
                        Cell cellD = row.createCell(cantCol);
                        DataFormat df = workbook.createDataFormat();
                        styleD.setDataFormat(df.getFormat("0.00"));
                        cellD.setCellValue(Double.valueOf(tabla[cantLin][cantCol]));
                        cellD.setCellStyle(styleD);
                    }
                }
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
