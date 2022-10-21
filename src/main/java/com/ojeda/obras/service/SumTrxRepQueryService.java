package com.ojeda.obras.service;

import com.ojeda.obras.domain.SumTrxRep;
import com.ojeda.obras.domain.SumTrxRep_;
import com.ojeda.obras.repository.SumTrxRepRepository;
import com.ojeda.obras.service.criteria.AdvPendRepCriteria;
import com.ojeda.obras.service.criteria.SumTrxRepCriteria;
import com.ojeda.obras.service.dto.SumTrxRepDTO;
import com.ojeda.obras.service.mapper.SumTrxRepMapper;
import java.time.ZoneId;
import java.util.Date;
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
 * Service for executing complex queries for {@link SumTrxRep} entities in the database.
 * The main input is a {@link SumTrxRepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SumTrxRepDTO} or a {@link Page} of {@link SumTrxRepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SumTrxRepQueryService extends QueryService<SumTrxRep> {

    private final Logger log = LoggerFactory.getLogger(SumTrxRepQueryService.class);

    private final SumTrxRepRepository sumTrxRepRepository;

    private final SumTrxRepMapper sumTrxRepMapper;

    public SumTrxRepQueryService(SumTrxRepRepository sumTrxRepRepository, SumTrxRepMapper sumTrxRepMapper) {
        this.sumTrxRepRepository = sumTrxRepRepository;
        this.sumTrxRepMapper = sumTrxRepMapper;
    }

    /**
     * Return a {@link List} of {@link SumTrxRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SumTrxRepDTO> findByCriteria(SumTrxRepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        log.debug(criteria.getFecha().getLessThan().toString());
        log.debug(criteria.getFecha().getGreaterThan().toString());
        if (criteria.getFecha().getLessThan() != null) {
            //final Specification<SumTrxRep> specification = createSpecification(criteria);
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date dateFrom = Date.from(criteria.getFecha().getGreaterThan().atStartOfDay(defaultZoneId).toInstant());
            Date dateTo = Date.from(criteria.getFecha().getLessThan().atStartOfDay(defaultZoneId).toInstant());
            return sumTrxRepMapper.toDto(
                sumTrxRepRepository.findByFechaBetweenAndObraId(dateFrom, dateTo, criteria.getObraId().getEquals())
            );
        } else {
            final Specification<SumTrxRep> specification = createSpecification(criteria);
            return sumTrxRepMapper.toDto(sumTrxRepRepository.findAll(specification));
        }
    }

    /**
     * Return a {@link Page} of {@link SumTrxRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SumTrxRepDTO> findByCriteria(SumTrxRepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SumTrxRep> specification = createSpecification(criteria);
        return sumTrxRepRepository.findAll(specification, page).map(sumTrxRepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SumTrxRepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SumTrxRep> specification = createSpecification(criteria);
        return sumTrxRepRepository.count(specification);
    }

    /**
     * Function to convert {@link SumTrxRepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SumTrxRep> createSpecification(SumTrxRepCriteria criteria) {
        Specification<SumTrxRep> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SumTrxRep_.id));
            }
            if (criteria.getObraId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getObraId(), SumTrxRep_.obraId));
            }
            if (criteria.getObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObra(), SumTrxRep_.obra));
            }
            if (criteria.getSubcontratista() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubcontratista(), SumTrxRep_.subcontratista));
            }
        }
        return specification;
    }
}
