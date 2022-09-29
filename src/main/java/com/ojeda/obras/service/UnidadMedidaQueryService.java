package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.UnidadMedida;
import com.ojeda.obras.repository.UnidadMedidaRepository;
import com.ojeda.obras.service.criteria.UnidadMedidaCriteria;
import com.ojeda.obras.service.dto.UnidadMedidaDTO;
import com.ojeda.obras.service.mapper.UnidadMedidaMapper;
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
 * Service for executing complex queries for {@link UnidadMedida} entities in the database.
 * The main input is a {@link UnidadMedidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UnidadMedidaDTO} or a {@link Page} of {@link UnidadMedidaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnidadMedidaQueryService extends QueryService<UnidadMedida> {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaQueryService.class);

    private final UnidadMedidaRepository unidadMedidaRepository;

    private final UnidadMedidaMapper unidadMedidaMapper;

    public UnidadMedidaQueryService(UnidadMedidaRepository unidadMedidaRepository, UnidadMedidaMapper unidadMedidaMapper) {
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.unidadMedidaMapper = unidadMedidaMapper;
    }

    /**
     * Return a {@link List} of {@link UnidadMedidaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UnidadMedidaDTO> findByCriteria(UnidadMedidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UnidadMedida> specification = createSpecification(criteria);
        return unidadMedidaMapper.toDto(unidadMedidaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UnidadMedidaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadMedidaDTO> findByCriteria(UnidadMedidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UnidadMedida> specification = createSpecification(criteria);
        return unidadMedidaRepository.findAll(specification, page).map(unidadMedidaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnidadMedidaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UnidadMedida> specification = createSpecification(criteria);
        return unidadMedidaRepository.count(specification);
    }

    /**
     * Function to convert {@link UnidadMedidaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UnidadMedida> createSpecification(UnidadMedidaCriteria criteria) {
        Specification<UnidadMedida> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UnidadMedida_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UnidadMedida_.name));
            }
        }
        return specification;
    }
}
