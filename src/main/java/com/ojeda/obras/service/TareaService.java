package com.ojeda.obras.service;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.repository.ConceptoRepository;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.repository.SubcontratistaRepository;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.dto.TareaDTO;
import com.ojeda.obras.service.mapper.TareaMapper;
import java.io.*;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link Tarea}.
 */
@Service
@Transactional
public class TareaService {

    private final Logger log = LoggerFactory.getLogger(TareaService.class);

    private final TareaRepository tareaRepository;

    private final SubcontratistaRepository subcontratistaRepository;

    private final ConceptoRepository conceptoRepository;

    private final ObraRepository obraRepository;

    private final TareaMapper tareaMapper;

    public TareaService(
        TareaRepository tareaRepository,
        TareaMapper tareaMapper,
        SubcontratistaRepository subcontratistaRepository,
        ConceptoRepository conceptoRepository,
        ObraRepository obraRepository
    ) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
        this.subcontratistaRepository = subcontratistaRepository;
        this.conceptoRepository = conceptoRepository;
        this.obraRepository = obraRepository;
    }

    /**
     * Save a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    public TareaDTO save(TareaDTO tareaDTO) {
        log.debug("Request to save Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    /**
     * Update a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    public TareaDTO update(TareaDTO tareaDTO) {
        log.debug("Request to update Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    /**
     * Partially update a tarea.
     *
     * @param tareaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TareaDTO> partialUpdate(TareaDTO tareaDTO) {
        log.debug("Request to partially update Tarea : {}", tareaDTO);

        return tareaRepository
            .findById(tareaDTO.getId())
            .map(existingTarea -> {
                tareaMapper.partialUpdate(existingTarea, tareaDTO);

                return existingTarea;
            })
            .map(tareaRepository::save)
            .map(tareaMapper::toDto);
    }

    /**
     * Get all the tareas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TareaDTO> findAll() {
        log.debug("Request to get all Tareas");
        return tareaRepository.findAll().stream().map(tareaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the tareas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TareaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tareaRepository.findAllWithEagerRelationships(pageable).map(tareaMapper::toDto);
    }

    /**
     * Get one tarea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TareaDTO> findOne(Long id) {
        log.debug("Request to get Tarea : {}", id);
        return tareaRepository.findOneWithEagerRelationships(id).map(tareaMapper::toDto);
    }

    /**
     * Delete the tarea by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tarea : {}", id);
        tareaRepository.deleteById(id);
    }

    public List<Tarea> findAllByObraId(Long id) {
        log.debug("Request to get All Tareas by Obra Id : {}", id);
        return tareaRepository.findAllByObraId(id);
    }

    public List<Tarea> findAllByObraName(String obraName) {
        log.debug("Request to get All Tareas by Obra Name : {}", obraName);
        return tareaRepository.findAllByObraName(obraName);
    }

    public File generateFile(List<TareaDTO> tareas) throws IOException, URISyntaxException {
        log.debug("GenerateFile Service: {}", tareas);

        /* Genero el excel */
        Workbook workbook = new XSSFWorkbook();

        /* Genero la Hoja General*/

        Sheet sheet = workbook.createSheet("Actualizar Tareas");

        sheet.setColumnWidth(0, 4200); // ID Tarea
        sheet.setColumnWidth(1, 4200); // Obra
        sheet.setColumnWidth(2, 4200); // Subcontratista
        sheet.setColumnWidth(3, 10000); // Descripcion
        sheet.setColumnWidth(4, 4200); // Concepto
        sheet.setColumnWidth(5, 4200); // cantidad
        sheet.setColumnWidth(6, 4200); // Costo Mano de Obra
        sheet.setColumnWidth(7, 4200); // Porcentaje

        Row title = sheet.createRow(0);
        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont fontTitle = ((XSSFWorkbook) workbook).createFont();
        fontTitle.setFontName("Arial");
        fontTitle.setFontHeightInPoints((short) 10);
        fontTitle.setBold(true);
        titleStyle.setFont(fontTitle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Cell titleCell = title.createCell(0);
        titleCell.setCellValue("ID Tarea (No Modificar)");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(1);
        titleCell.setCellValue("Obra");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(2);
        titleCell.setCellValue("Subcontratista");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(3);
        titleCell.setCellValue("Descripcion");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(4);
        titleCell.setCellValue("Concepto");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(5);
        titleCell.setCellValue("Cantidad");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(6);
        titleCell.setCellValue("Costo");
        titleCell.setCellStyle(titleStyle);

        titleCell = title.createCell(7);
        titleCell.setCellValue("Porc. Avance");
        titleCell.setCellStyle(titleStyle);

        int i = 1;
        Row tareaRow = null;

        XSSFFont fontI = ((XSSFWorkbook) workbook).createFont();
        fontI.setFontName("Arial");
        fontI.setFontHeightInPoints((short) 10);
        fontI.setBold(false);

        CellStyle styleS = workbook.createCellStyle();
        styleS.setFont(fontI);
        styleS.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleM = workbook.createCellStyle();
        styleM.setFont(fontI);
        styleM.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfMon = workbook.createDataFormat();
        styleM.setDataFormat(dfMon.getFormat("$0.00"));

        CellStyle styleD = workbook.createCellStyle();
        styleD.setFont(fontI);
        styleD.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfDec = workbook.createDataFormat();
        styleD.setDataFormat(dfDec.getFormat("0.00"));

        CellStyle styleP = workbook.createCellStyle();
        styleP.setFont(fontI);
        styleP.setAlignment(HorizontalAlignment.RIGHT);
        DataFormat dfPor = workbook.createDataFormat();
        styleP.setDataFormat(dfPor.getFormat("0.00%"));

        Cell tareaCell = null;

        for (TareaDTO tarea : tareas) {
            i++;
            tareaRow = sheet.createRow(i);

            tareaCell = tareaRow.createCell(0); // ID Tarea
            tareaCell.setCellValue(tarea.getId());
            tareaCell.setCellStyle(styleS);

            tareaCell = tareaRow.createCell(1); // Obra
            tareaCell.setCellValue(tarea.getObra().getName());
            tareaCell.setCellStyle(styleS);

            tareaCell = tareaRow.createCell(2); // Subcontratista
            tareaCell.setCellValue(tarea.getSubcontratista().getLastName() + ", " + tarea.getSubcontratista().getFirstName());
            tareaCell.setCellStyle(styleS);

            tareaCell = tareaRow.createCell(3); // Descripcion
            tareaCell.setCellValue(tarea.getName());
            tareaCell.setCellStyle(styleS);

            tareaCell = tareaRow.createCell(4); // Concepto
            tareaCell.setCellValue(tarea.getConcepto().getName());
            tareaCell.setCellStyle(styleS);

            tareaCell = tareaRow.createCell(5); // Cantidad
            tareaCell.setCellValue(tarea.getQuantity());
            tareaCell.setCellStyle(styleD);

            tareaCell = tareaRow.createCell(6); // Mano de Obra
            tareaCell.setCellValue(tarea.getCost());
            tareaCell.setCellStyle(styleD);

            tareaCell = tareaRow.createCell(7); // Porcentaje de Mano de Obra
            tareaCell.setCellValue((tarea.getAdvanceStatus() / 100));
            tareaCell.setCellStyle(styleP);
        }

        /*List<Concepto> conceptos = conceptoRepository.findAll();
        String[] concs = new String[conceptos.size()];
        int conCant = 0;
        for (Concepto con: conceptos) {
            concs[conCant] = con.getName();
            conCant ++;
        }*/

        /* Genero la hoja de datos de Subcontratistas*/

        Sheet sheetD = workbook.createSheet("Datos_Subcontratistas");
        sheetD.protectSheet("obras"); // Bajo el password obras
        workbook.setSheetVisibility(workbook.getSheetIndex("Datos_Subcontratistas"), SheetVisibility.VERY_HIDDEN);

        Row titleS = sheetD.createRow(0);

        sheetD.setColumnWidth(0, 4200); // Subcontratistas
        sheetD.setColumnWidth(1, 4200); // Conceptos

        Cell titleCellD = null;

        titleCellD = titleS.createCell(0);
        titleCellD.setCellValue("Subcontratistas");

        Cell subCoCellD = null;

        List<Subcontratista> subcontratistas = subcontratistaRepository.findAll();

        int s = 0;
        Row subcoRow = null;

        for (Subcontratista sub : subcontratistas) {
            s++;
            subcoRow = sheetD.createRow(s);
            subCoCellD = subcoRow.createCell(0);
            subCoCellD.setCellValue(sub.getLastName() + ", " + sub.getFirstName());
        }
        int subc = s + 1;

        String referenceS = sheetD.getSheetName() + "!$A$2:$A" + "$" + subc;

        log.debug(referenceS);

        Name namedAreaS = workbook.createName();
        namedAreaS.setNameName("SubcoDataArea");
        namedAreaS.setRefersToFormula(referenceS);

        CellRangeAddressList addressListrmCategory = new CellRangeAddressList(2, 99999, 2, 2);

        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("SubcoDataArea");
        DataValidation subcoValidation = dvHelper.createValidation(dvConstraint, addressListrmCategory);

        sheet.addValidationData(subcoValidation);

        Sheet sheetC = workbook.createSheet("Datos_Conceptos");
        sheetC.protectSheet("obras"); // Bajo el password obras
        workbook.setSheetVisibility(workbook.getSheetIndex("Datos_Conceptos"), SheetVisibility.VERY_HIDDEN);

        Row titleC = sheetC.createRow(0);

        sheetC.setColumnWidth(0, 4200); // Subcontratistas
        sheetC.setColumnWidth(1, 4200); // Conceptos

        Cell titleCellC = null;

        titleCellC = titleC.createCell(0);
        titleCellC.setCellValue("Conceptos");

        Cell subCoCellC = null;

        List<Concepto> conceptos = conceptoRepository.findAll();

        int c = 0;
        Row conceptoRow = null;

        for (Concepto con : conceptos) {
            c++;
            conceptoRow = sheetC.createRow(c);
            subCoCellC = conceptoRow.createCell(0);
            subCoCellC.setCellValue(con.getName());
        }
        int conc = c + 1;

        String referenceC = sheetC.getSheetName() + "!$A$2:$A" + "$" + conc;

        log.debug(referenceC);

        Name namedAreaC = workbook.createName();
        namedAreaC.setNameName("ConceptoDataArea");
        namedAreaC.setRefersToFormula(referenceC);

        CellRangeAddressList addressListrmConcepto = new CellRangeAddressList(2, 99999, 4, 4);

        DataValidationHelper dvHelperC = sheet.getDataValidationHelper();
        DataValidationConstraint dvConstraintC = dvHelperC.createFormulaListConstraint("ConceptoDataArea");
        DataValidation subcoValidationC = dvHelperC.createValidation(dvConstraintC, addressListrmConcepto);

        sheet.addValidationData(subcoValidationC);

        File outputFile = File.createTempFile("temp", ".xls");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }

    public Boolean submitXLS(MultipartFile file) throws IOException {
        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
        FileOutputStream f = new FileOutputStream(fileLocation);
        int ch = 0;
        while ((ch = inputStream.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();
        log.debug("File: " + file.getOriginalFilename() + " has been uploaded successfully!");

        FileInputStream fis = new FileInputStream(fileLocation);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        int r = 0;
        for (Row row : sheet) { //iteration over row using for each loop
            r++;
            if (r > 1) {
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() != CellType.NUMERIC) {
                        log.debug(row.getCell(0).getStringCellValue());
                    } else {
                        log.debug("ID Tarea: {}", Double.valueOf(row.getCell(0).getNumericCellValue()).longValue());
                        Optional<Tarea> tar = tareaRepository.findOneWithEagerRelationships(
                            Double.valueOf(row.getCell(0).getNumericCellValue()).longValue()
                        );
                        if (tar.isPresent()) {
                            log.debug("is present");
                            Tarea tar2 = new Tarea();
                            tar2.setId(tar.get().getId());
                            tar2.setObra(tar.get().getObra());
                            tar2.setSubcontratista(
                                subcontratistaRepository.findByLastNameAndFirstName(
                                    row.getCell(2).getStringCellValue().substring(0, row.getCell(2).getStringCellValue().indexOf(",")),
                                    row.getCell(2).getStringCellValue().substring(row.getCell(2).getStringCellValue().indexOf(",") + 2)
                                )
                            );
                            tar2.setName(row.getCell(3).getStringCellValue());
                            tar2.setConcepto(conceptoRepository.findByName(row.getCell(4).getStringCellValue()));
                            tar2.setQuantity(row.getCell(5).getNumericCellValue());
                            tar2.setCost(row.getCell(6).getNumericCellValue());
                            tar2.setAdvanceStatus(Double.valueOf(row.getCell(7).getNumericCellValue()) * 100);
                            TareaDTO result = tareaMapper.toDto(tar2);
                            result.setId(tar.get().getId());
                            result = update(result);
                        } else {
                            Tarea tar2 = new Tarea();
                            tar2.setObra(obraRepository.findByName(row.getCell(1).getStringCellValue()));
                            tar2.setName(row.getCell(3).getStringCellValue());
                            tar2.setConcepto(conceptoRepository.findByName(row.getCell(4).getStringCellValue()));
                            tar2.setQuantity(row.getCell(5).getNumericCellValue());
                            tar2.setCost(row.getCell(6).getNumericCellValue());
                            tar2.setAdvanceStatus(Double.valueOf(row.getCell(7).getNumericCellValue()) * 100);
                            TareaDTO result = save(tareaMapper.toDto(tar2));
                        }
                    }
                } else {
                    log.debug("Este es un registro nuevo");
                    Tarea tar2 = new Tarea();

                    Obra o = new Obra();
                    if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                        log.debug("Obra: {}", String.valueOf(row.getCell(1).getNumericCellValue()));
                        o = obraRepository.findByName(String.valueOf(row.getCell(1).getNumericCellValue()));
                    } else {
                        log.debug("Obra: {}", row.getCell(1).getStringCellValue());
                        o = obraRepository.findByName(row.getCell(1).getStringCellValue());
                    }
                    log.debug("Obra: {}", o.getName());
                    tar2.setObra(o);
                    tar2.setSubcontratista(
                        subcontratistaRepository.findByLastNameAndFirstName(
                            row.getCell(2).getStringCellValue().substring(0, row.getCell(2).getStringCellValue().indexOf(",")),
                            row.getCell(2).getStringCellValue().substring(row.getCell(2).getStringCellValue().indexOf(",") + 2)
                        )
                    );
                    tar2.setName(row.getCell(3).getStringCellValue());
                    log.debug("Name OK");
                    tar2.setConcepto(conceptoRepository.findByName(row.getCell(4).getStringCellValue()));
                    log.debug("Concepto OK");
                    tar2.setQuantity(row.getCell(5).getNumericCellValue());
                    log.debug("Quantity OK");
                    tar2.setCost(row.getCell(6).getNumericCellValue());
                    log.debug("Cost OK");
                    tar2.setAdvanceStatus(Double.valueOf(row.getCell(7).getNumericCellValue()) * 100);
                    log.debug("status OK");
                    TareaDTO result = tareaMapper.toDto(tar2);
                    result = save(result);
                }
            }
        }
        return Boolean.TRUE;
    }
}
