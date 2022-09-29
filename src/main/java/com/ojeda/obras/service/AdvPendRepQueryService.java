package com.ojeda.obras.service;

import com.ojeda.obras.domain.AdvPendRep;
import com.ojeda.obras.domain.AdvPendRep_;
import com.ojeda.obras.repository.AdvPendRepRepository;
import com.ojeda.obras.service.criteria.AdvPendRepCriteria;
import com.ojeda.obras.service.dto.AdvPendRepDTO;
import com.ojeda.obras.service.mapper.AdvPendRepMapper;
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
 * Service for executing complex queries for {@link AdvPendRep} entities in the database.
 * The main input is a {@link AdvPendRepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdvPendRepDTO} or a {@link Page} of {@link AdvPendRepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdvPendRepQueryService extends QueryService<AdvPendRep> {

    private final Logger log = LoggerFactory.getLogger(AdvPendRepQueryService.class);

    private final AdvPendRepRepository advPendRepRepository;

    private final AdvPendRepMapper advPendRepMapper;

    public AdvPendRepQueryService(AdvPendRepRepository advPendRepRepository, AdvPendRepMapper advPendRepMapper) {
        this.advPendRepRepository = advPendRepRepository;
        this.advPendRepMapper = advPendRepMapper;
    }

    /**
     * Return a {@link List} of {@link AdvPendRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdvPendRepDTO> findByCriteria(AdvPendRepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdvPendRep> specification = createSpecification(criteria);
        return advPendRepMapper.toDto(advPendRepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdvPendRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdvPendRepDTO> findByCriteria(AdvPendRepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdvPendRep> specification = createSpecification(criteria);
        return advPendRepRepository.findAll(specification, page).map(advPendRepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdvPendRepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdvPendRep> specification = createSpecification(criteria);
        return advPendRepRepository.count(specification);
    }

    /**
     * Function to convert {@link AdvPendRepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdvPendRep> createSpecification(AdvPendRepCriteria criteria) {
        Specification<AdvPendRep> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdvPendRep_.id));
            }
            if (criteria.getObraId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getObraId(), AdvPendRep_.obraId));
            }
            if (criteria.getObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObra(), AdvPendRep_.obra));
            }
            if (criteria.getSubcontratistaId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubcontratistaId(), AdvPendRep_.subcontratistaId));
            }
            if (criteria.getSubcontratista() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubcontratista(), AdvPendRep_.subcontratista));
            }
        }
        return specification;
    }
}
