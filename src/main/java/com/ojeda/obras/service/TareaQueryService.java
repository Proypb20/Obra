package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Tarea;
import com.ojeda.obras.repository.TareaRepository;
import com.ojeda.obras.service.criteria.TareaCriteria;
import com.ojeda.obras.service.dto.TareaDTO;
import com.ojeda.obras.service.mapper.TareaMapper;
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
 * Service for executing complex queries for {@link Tarea} entities in the database.
 * The main input is a {@link TareaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TareaDTO} or a {@link Page} of {@link TareaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TareaQueryService extends QueryService<Tarea> {

    private final Logger log = LoggerFactory.getLogger(TareaQueryService.class);

    private final TareaRepository tareaRepository;

    private final TareaMapper tareaMapper;

    public TareaQueryService(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    /**
     * Return a {@link List} of {@link TareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TareaDTO> findByCriteria(TareaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaMapper.toDto(tareaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TareaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TareaDTO> findByCriteria(TareaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaRepository.findAll(specification, page).map(tareaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TareaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tarea> specification = createSpecification(criteria);
        return tareaRepository.count(specification);
    }

    /**
     * Function to convert {@link TareaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tarea> createSpecification(TareaCriteria criteria) {
        Specification<Tarea> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tarea_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Tarea_.name));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Tarea_.quantity));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Tarea_.cost));
            }
            if (criteria.getAdvanceStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvanceStatus(), Tarea_.advanceStatus));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Tarea_.obra, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getSubcontratistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubcontratistaId(),
                            root -> root.join(Tarea_.subcontratista, JoinType.LEFT).get(Subcontratista_.id)
                        )
                    );
            }
            if (criteria.getConceptoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getConceptoId(), root -> root.join(Tarea_.concepto, JoinType.LEFT).get(Concepto_.id))
                    );
            }
        }
        return specification;
    }
}
