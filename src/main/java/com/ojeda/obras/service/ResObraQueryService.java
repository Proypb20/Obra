package com.ojeda.obras.service;

import com.ojeda.obras.domain.ResObra;
import com.ojeda.obras.domain.ResObra_;
import com.ojeda.obras.repository.ResObraRepository;
import com.ojeda.obras.service.criteria.ResObraCriteria;
import com.ojeda.obras.service.dto.ResObraDTO;
import com.ojeda.obras.service.mapper.ResObraMapper;
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
 * Service for executing complex queries for {@link ResObra} entities in the database.
 * The main input is a {@link ResObraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResObraDTO} or a {@link Page} of {@link ResObraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResObraQueryService extends QueryService<ResObra> {

    private final Logger log = LoggerFactory.getLogger(ResObraQueryService.class);

    private final ResObraRepository resObraRepository;

    private final ResObraMapper resObraMapper;

    public ResObraQueryService(ResObraRepository resObraRepository, ResObraMapper resObraMapper) {
        this.resObraRepository = resObraRepository;
        this.resObraMapper = resObraMapper;
    }

    /**
     * Return a {@link List} of {@link ResObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResObraDTO> findByCriteria(ResObraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResObra> specification = createSpecification(criteria);
        return resObraMapper.toDto(resObraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResObraDTO> findByCriteria(ResObraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResObra> specification = createSpecification(criteria);
        return resObraRepository.findAll(specification, page).map(resObraMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ResObra> findAllByObraName(String obraName) {
        log.debug("find by obraName : {}", obraName);
        return resObraRepository.findAllByObraName(obraName);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResObraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResObra> specification = createSpecification(criteria);
        return resObraRepository.count(specification);
    }

    /**
     * Function to convert {@link ResObraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResObra> createSpecification(ResObraCriteria criteria) {
        Specification<ResObra> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResObra_.id));
            }
            if (criteria.getObraName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObraName(), ResObra_.obraName));
            }
            if (criteria.getPeriodName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriodName(), ResObra_.periodName));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), ResObra_.source));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), ResObra_.reference));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ResObra_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ResObra_.type));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), ResObra_.amount));
            }
        }
        return specification;
    }
}
