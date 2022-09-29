package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.TipoComprobante;
import com.ojeda.obras.repository.TipoComprobanteRepository;
import com.ojeda.obras.service.criteria.TipoComprobanteCriteria;
import com.ojeda.obras.service.dto.TipoComprobanteDTO;
import com.ojeda.obras.service.mapper.TipoComprobanteMapper;
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
 * Service for executing complex queries for {@link TipoComprobante} entities in the database.
 * The main input is a {@link TipoComprobanteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoComprobanteDTO} or a {@link Page} of {@link TipoComprobanteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoComprobanteQueryService extends QueryService<TipoComprobante> {

    private final Logger log = LoggerFactory.getLogger(TipoComprobanteQueryService.class);

    private final TipoComprobanteRepository tipoComprobanteRepository;

    private final TipoComprobanteMapper tipoComprobanteMapper;

    public TipoComprobanteQueryService(TipoComprobanteRepository tipoComprobanteRepository, TipoComprobanteMapper tipoComprobanteMapper) {
        this.tipoComprobanteRepository = tipoComprobanteRepository;
        this.tipoComprobanteMapper = tipoComprobanteMapper;
    }

    /**
     * Return a {@link List} of {@link TipoComprobanteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoComprobanteDTO> findByCriteria(TipoComprobanteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoComprobante> specification = createSpecification(criteria);
        return tipoComprobanteMapper.toDto(tipoComprobanteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoComprobanteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoComprobanteDTO> findByCriteria(TipoComprobanteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoComprobante> specification = createSpecification(criteria);
        return tipoComprobanteRepository.findAll(specification, page).map(tipoComprobanteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoComprobanteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoComprobante> specification = createSpecification(criteria);
        return tipoComprobanteRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoComprobanteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoComprobante> createSpecification(TipoComprobanteCriteria criteria) {
        Specification<TipoComprobante> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoComprobante_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TipoComprobante_.name));
            }
        }
        return specification;
    }
}
