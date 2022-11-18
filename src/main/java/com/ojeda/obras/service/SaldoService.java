package com.ojeda.obras.service;

import com.ojeda.obras.domain.Saldo;
import com.ojeda.obras.repository.SaldoRepository;
import com.ojeda.obras.service.dto.SaldoDTO;
import com.ojeda.obras.service.mapper.SaldoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Saldo}.
 */
@Service
@Transactional
public class SaldoService {

    private final Logger log = LoggerFactory.getLogger(SaldoService.class);

    private final SaldoRepository saldoRepository;

    private final SaldoMapper saldoMapper;

    public SaldoService(SaldoRepository saldoRepository, SaldoMapper saldoMapper) {
        this.saldoRepository = saldoRepository;
        this.saldoMapper = saldoMapper;
    }

    /**
     * Get all the sumTrx.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SaldoDTO> findAll() {
        log.debug("Request to get all saldos");
        return saldoRepository.findAll().stream().map(saldoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
