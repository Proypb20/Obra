package com.ojeda.obras.service;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.domain.AdvObraRep_;
import com.ojeda.obras.repository.AdvObraRepRepository;
import com.ojeda.obras.service.criteria.AdvObraRepCriteria;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import com.ojeda.obras.service.mapper.AdvObraRepMapper;
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
 * Service for executing complex queries for {@link AdvObraRep} entities in the database.
 * The main input is a {@link AdvObraRepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdvObraRepDTO} or a {@link Page} of {@link AdvObraRepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdvObraRepQueryService extends QueryService<AdvObraRep> {

    private final Logger log = LoggerFactory.getLogger(AdvObraRepQueryService.class);

    private final AdvObraRepRepository advObraRepRepository;

    private final AdvObraRepMapper advObraRepMapper;

    public AdvObraRepQueryService(AdvObraRepRepository advObraRepRepository, AdvObraRepMapper advObraRepMapper) {
        this.advObraRepRepository = advObraRepRepository;
        this.advObraRepMapper = advObraRepMapper;
    }

    /**
     * Return a {@link List} of {@link AdvObraRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdvObraRepDTO> findByCriteria(AdvObraRepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdvObraRep> specification = createSpecification(criteria);
        return advObraRepMapper.toDto(advObraRepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdvObraRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdvObraRepDTO> findByCriteria(AdvObraRepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdvObraRep> specification = createSpecification(criteria);
        return advObraRepRepository.findAll(specification, page).map(advObraRepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdvObraRepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdvObraRep> specification = createSpecification(criteria);
        return advObraRepRepository.count(specification);
    }

    /**
     * Function to convert {@link AdvObraRepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdvObraRep> createSpecification(AdvObraRepCriteria criteria) {
        Specification<AdvObraRep> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdvObraRep_.id));
            }
            if (criteria.getObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObra(), AdvObraRep_.obra));
            }
            if (criteria.getObraId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getObraId(), AdvObraRep_.obraId));
            }
            if (criteria.getConcepto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConcepto(), AdvObraRep_.concepto));
            }
            if (criteria.getConceptoId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConceptoId(), AdvObraRep_.conceptoId));
            }
            if (criteria.getTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaskName(), AdvObraRep_.taskName));
            }
        }
        return specification;
    }
}
