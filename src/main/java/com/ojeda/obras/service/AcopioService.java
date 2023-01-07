package com.ojeda.obras.service;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.repository.AcopioRepository;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.service.mapper.AcopioMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Acopio}.
 */
@Service
@Transactional
public class AcopioService {

    private final Logger log = LoggerFactory.getLogger(AcopioService.class);

    private final AcopioRepository acopioRepository;

    private final AcopioMapper acopioMapper;

    public AcopioService(AcopioRepository acopioRepository, AcopioMapper acopioMapper) {
        this.acopioRepository = acopioRepository;
        this.acopioMapper = acopioMapper;
    }

    /**
     * Save a acopio.
     *
     * @param acopioDTO the entity to save.
     * @return the persisted entity.
     */
    public AcopioDTO save(AcopioDTO acopioDTO) {
        log.debug("Request to save Acopio : {}", acopioDTO);
        Acopio acopio = acopioMapper.toEntity(acopioDTO);
        acopio = acopioRepository.save(acopio);
        return acopioMapper.toDto(acopio);
    }

    /**
     * Update a acopio.
     *
     * @param acopioDTO the entity to save.
     * @return the persisted entity.
     */
    public AcopioDTO update(AcopioDTO acopioDTO) {
        log.debug("Request to update Acopio : {}", acopioDTO);
        Acopio acopio = acopioMapper.toEntity(acopioDTO);
        acopio = acopioRepository.save(acopio);
        return acopioMapper.toDto(acopio);
    }

    /**
     * Partially update a acopio.
     *
     * @param acopioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AcopioDTO> partialUpdate(AcopioDTO acopioDTO) {
        log.debug("Request to partially update Acopio : {}", acopioDTO);

        return acopioRepository
            .findById(acopioDTO.getId())
            .map(existingAcopio -> {
                acopioMapper.partialUpdate(existingAcopio, acopioDTO);

                return existingAcopio;
            })
            .map(acopioRepository::save)
            .map(acopioMapper::toDto);
    }

    /**
     * Get all the acopios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AcopioDTO> findAll() {
        log.debug("Request to get all Acopios");
        return acopioRepository.findAll().stream().map(acopioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the acopios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AcopioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return acopioRepository.findAllWithEagerRelationships(pageable).map(acopioMapper::toDto);
    }

    /**
     * Get one acopio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AcopioDTO> findOne(Long id) {
        log.debug("Request to get Acopio : {}", id);
        return acopioRepository.findOneWithEagerRelationships(id).map(acopioMapper::toDto);
    }

    /**
     * Delete the acopio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Acopio : {}", id);
        acopioRepository.deleteById(id);
    }

    public File generateFile(AcopioDTO acopio, List<DetalleAcopio> detalleAcopios) throws IOException, URISyntaxException {
        log.debug("Generate File Service");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ACOPIO");

        sheet.setColumnWidth(0, 2140);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 3200);
        sheet.setColumnWidth(4, 3200);
        sheet.setColumnWidth(5, 3200);
        sheet.setColumnWidth(6, 3200);

        File outputFile = File.createTempFile("temp", ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
            workbook.close();
        }
        return outputFile;
    }

    public Double getSumAmount(Long id) {
        Double sal = acopioRepository.getSumAmount(id);
        if (sal == null) {
            sal = 0D;
        }
        return sal;
    }
}
