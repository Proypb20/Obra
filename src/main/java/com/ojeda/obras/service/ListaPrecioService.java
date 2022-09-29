package com.ojeda.obras.service;

import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.repository.DetalleListaPrecioRepository;
import com.ojeda.obras.repository.ListaPrecioRepository;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import com.ojeda.obras.service.mapper.ListaPrecioMapper;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link ListaPrecio}.
 */
@Service
@Transactional
public class ListaPrecioService {

    private final Logger log = LoggerFactory.getLogger(ListaPrecioService.class);

    private final ListaPrecioRepository listaPrecioRepository;

    private final DetalleListaPrecioRepository detalleListaPrecioRepository;

    private final ListaPrecioMapper listaPrecioMapper;

    public ListaPrecioService(
        ListaPrecioRepository listaPrecioRepository,
        DetalleListaPrecioRepository detalleListaPrecioRepository,
        ListaPrecioMapper listaPrecioMapper
    ) {
        this.listaPrecioRepository = listaPrecioRepository;
        this.listaPrecioMapper = listaPrecioMapper;
        this.detalleListaPrecioRepository = detalleListaPrecioRepository;
    }

    /**
     * Save a listaPrecio.
     *
     * @param listaPrecioDTO the entity to save.
     * @return the persisted entity.
     */
    public ListaPrecioDTO save(ListaPrecioDTO listaPrecioDTO) {
        log.debug("Request to save ListaPrecio : {}", listaPrecioDTO);
        ListaPrecio listaPrecio = listaPrecioMapper.toEntity(listaPrecioDTO);
        listaPrecio = listaPrecioRepository.save(listaPrecio);
        return listaPrecioMapper.toDto(listaPrecio);
    }

    /**
     * Update a listaPrecio.
     *
     * @param listaPrecioDTO the entity to save.
     * @return the persisted entity.
     */
    public ListaPrecioDTO update(ListaPrecioDTO listaPrecioDTO) {
        log.debug("Request to update ListaPrecio : {}", listaPrecioDTO);
        ListaPrecio listaPrecio = listaPrecioMapper.toEntity(listaPrecioDTO);
        listaPrecio = listaPrecioRepository.save(listaPrecio);
        return listaPrecioMapper.toDto(listaPrecio);
    }

    /**
     * Partially update a listaPrecio.
     *
     * @param listaPrecioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ListaPrecioDTO> partialUpdate(ListaPrecioDTO listaPrecioDTO) {
        log.debug("Request to partially update ListaPrecio : {}", listaPrecioDTO);

        return listaPrecioRepository
            .findById(listaPrecioDTO.getId())
            .map(existingListaPrecio -> {
                listaPrecioMapper.partialUpdate(existingListaPrecio, listaPrecioDTO);

                return existingListaPrecio;
            })
            .map(listaPrecioRepository::save)
            .map(listaPrecioMapper::toDto);
    }

    /**
     * Get all the listaPrecios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListaPrecioDTO> findAll() {
        log.debug("Request to get all ListaPrecios");
        return listaPrecioRepository.findAll().stream().map(listaPrecioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the listaPrecios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ListaPrecioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return listaPrecioRepository.findAllWithEagerRelationships(pageable).map(listaPrecioMapper::toDto);
    }

    /**
     * Get one listaPrecio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListaPrecioDTO> findOne(Long id) {
        log.debug("Request to get ListaPrecio : {}", id);
        return listaPrecioRepository.findOneWithEagerRelationships(id).map(listaPrecioMapper::toDto);
    }

    /**
     * Delete the listaPrecio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListaPrecio : {}", id);
        listaPrecioRepository.deleteById(id);
    }

    public Boolean submitXML(MultipartFile file) throws IOException {
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

        ListaPrecio lp = new ListaPrecio();
        lp.setName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
        lp.setDate(LocalDateTime.now().toLocalDate());
        lp = listaPrecioRepository.save(lp);

        FileInputStream fis = new FileInputStream(fileLocation);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        for (Row row : sheet) { //iteration over row using for each loop
            DetalleListaPrecio dlp = new DetalleListaPrecio();
            log.debug("1");
            if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                dlp.setCode(row.getCell(0).getNumericCellValue() + "");
            } else {
                dlp.setCode(row.getCell(0).getStringCellValue());
            }
            log.debug("2");
            if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                dlp.setProduct(row.getCell(1).getNumericCellValue() + "");
            } else {
                dlp.setProduct(row.getCell(1).getStringCellValue());
            }
            log.debug("3");
            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                dlp.setAmount((float) row.getCell(2).getNumericCellValue());
            }
            log.debug("4");

            dlp.listaPrecio(lp);
            dlp = detalleListaPrecioRepository.save(dlp);
            /*for(Cell cell: row)    //iteration over cell using for each loop
            {
                switch(formulaEvaluator.evaluateInCell(cell).getCellType())
                {
                    case NUMERIC:   //field that represents numeric cell type
                         //getting the value of the cell as a number
                        log.debug(cell.getNumericCellValue()+ "\t\t");
                        break;
                    case STRING:    //field that represents string cell type
                        //getting the value of the cell as a string
                        log.debug(cell.getStringCellValue()+ "\t\t");
                        break;
                }
            }*/
        }
        return Boolean.TRUE;
    }
}
