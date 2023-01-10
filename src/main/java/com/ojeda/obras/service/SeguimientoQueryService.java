package com.ojeda.obras.service;

import com.ojeda.obras.domain.Seguimiento;
import com.ojeda.obras.domain.Seguimiento_;
import com.ojeda.obras.repository.SeguimientoRepository;
import com.ojeda.obras.service.criteria.SeguimientoCriteria;
import com.ojeda.obras.service.dto.SeguimientoDTO;
import com.ojeda.obras.service.mapper.SeguimientoMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Seguimiento} entities in the database.
 * The main input is a {@link SeguimientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SeguimientoDTO} or a {@link Page} of {@link SeguimientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SeguimientoQueryService extends QueryService<Seguimiento> {

    private final Logger log = LoggerFactory.getLogger(SeguimientoQueryService.class);

    private final SeguimientoRepository seguimientoRepository;

    private final SeguimientoMapper seguimientoMapper;

    public SeguimientoQueryService(SeguimientoRepository seguimientoRepository, SeguimientoMapper seguimientoMapper) {
        this.seguimientoRepository = seguimientoRepository;
        this.seguimientoMapper = seguimientoMapper;
    }

    /**
     * Return a {@link List} of {@link SeguimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SeguimientoDTO> findByCriteria(SeguimientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Seguimiento> specification = createSpecification(criteria);
        return seguimientoMapper.toDto(seguimientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SeguimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SeguimientoDTO> findByCriteria(SeguimientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Seguimiento> specification = createSpecification(criteria);
        return seguimientoRepository.findAll(specification, page).map(seguimientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SeguimientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Seguimiento> specification = createSpecification(criteria);
        return seguimientoRepository.count(specification);
    }

    /**
     * Function to convert {@link SeguimientoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Seguimiento> createSpecification(SeguimientoCriteria criteria) {
        Specification<Seguimiento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getObraName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObraName(), Seguimiento_.obraName));
            }
            if (criteria.getPeriodName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodName(), Seguimiento_.periodName));
            }
            if (criteria.getConceptName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConceptName(), Seguimiento_.conceptName));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Seguimiento_.amount));
            }
        }
        return specification;
    }
}
