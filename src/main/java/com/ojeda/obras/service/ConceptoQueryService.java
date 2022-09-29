package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.repository.ConceptoRepository;
import com.ojeda.obras.service.criteria.ConceptoCriteria;
import com.ojeda.obras.service.dto.ConceptoDTO;
import com.ojeda.obras.service.mapper.ConceptoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Concepto} entities in the database.
 * The main input is a {@link ConceptoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConceptoDTO} or a {@link Page} of {@link ConceptoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConceptoQueryService extends QueryService<Concepto> {

    private final Logger log = LoggerFactory.getLogger(ConceptoQueryService.class);

    private final ConceptoRepository conceptoRepository;

    private final ConceptoMapper conceptoMapper;

    public ConceptoQueryService(ConceptoRepository conceptoRepository, ConceptoMapper conceptoMapper) {
        this.conceptoRepository = conceptoRepository;
        this.conceptoMapper = conceptoMapper;
    }

    /**
     * Return a {@link List} of {@link ConceptoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConceptoDTO> findByCriteria(ConceptoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Concepto> specification = createSpecification(criteria);
        return conceptoMapper.toDto(conceptoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConceptoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConceptoDTO> findByCriteria(ConceptoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Concepto> specification = createSpecification(criteria);
        return conceptoRepository.findAll(specification, page).map(conceptoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConceptoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Concepto> specification = createSpecification(criteria);
        return conceptoRepository.count(specification);
    }

    /**
     * Function to convert {@link ConceptoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Concepto> createSpecification(ConceptoCriteria criteria) {
        Specification<Concepto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Concepto_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Concepto_.name));
            }
        }
        return specification;
    }
}
