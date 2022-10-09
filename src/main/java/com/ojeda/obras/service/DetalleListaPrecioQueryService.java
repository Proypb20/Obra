package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.DetalleListaPrecio;
import com.ojeda.obras.repository.DetalleListaPrecioRepository;
import com.ojeda.obras.service.criteria.DetalleListaPrecioCriteria;
import com.ojeda.obras.service.dto.DetalleListaPrecioDTO;
import com.ojeda.obras.service.mapper.DetalleListaPrecioMapper;
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
 * Service for executing complex queries for {@link DetalleListaPrecio} entities in the database.
 * The main input is a {@link DetalleListaPrecioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalleListaPrecioDTO} or a {@link Page} of {@link DetalleListaPrecioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalleListaPrecioQueryService extends QueryService<DetalleListaPrecio> {

    private final Logger log = LoggerFactory.getLogger(DetalleListaPrecioQueryService.class);

    private final DetalleListaPrecioRepository detalleListaPrecioRepository;

    private final DetalleListaPrecioMapper detalleListaPrecioMapper;

    public DetalleListaPrecioQueryService(
        DetalleListaPrecioRepository detalleListaPrecioRepository,
        DetalleListaPrecioMapper detalleListaPrecioMapper
    ) {
        this.detalleListaPrecioRepository = detalleListaPrecioRepository;
        this.detalleListaPrecioMapper = detalleListaPrecioMapper;
    }

    /**
     * Return a {@link List} of {@link DetalleListaPrecioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalleListaPrecioDTO> findByCriteria(DetalleListaPrecioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalleListaPrecio> specification = createSpecification(criteria);
        return detalleListaPrecioMapper.toDto(detalleListaPrecioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalleListaPrecioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalleListaPrecioDTO> findByCriteria(DetalleListaPrecioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalleListaPrecio> specification = createSpecification(criteria);
        return detalleListaPrecioRepository.findAll(specification, page).map(detalleListaPrecioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalleListaPrecioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalleListaPrecio> specification = createSpecification(criteria);
        return detalleListaPrecioRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalleListaPrecioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalleListaPrecio> createSpecification(DetalleListaPrecioCriteria criteria) {
        Specification<DetalleListaPrecio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalleListaPrecio_.id));
            }
            if (criteria.getProduct() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProduct(), DetalleListaPrecio_.product));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DetalleListaPrecio_.amount));
            }
            if (criteria.getListaPrecioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getListaPrecioId(),
                            root -> root.join(DetalleListaPrecio_.listaPrecio, JoinType.LEFT).get(ListaPrecio_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
