package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.repository.DetalleAcopioRepository;
import com.ojeda.obras.service.criteria.DetalleAcopioCriteria;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import com.ojeda.obras.service.mapper.DetalleAcopioMapper;
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
 * Service for executing complex queries for {@link DetalleAcopio} entities in the database.
 * The main input is a {@link DetalleAcopioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalleAcopioDTO} or a {@link Page} of {@link DetalleAcopioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalleAcopioQueryService extends QueryService<DetalleAcopio> {

    private final Logger log = LoggerFactory.getLogger(DetalleAcopioQueryService.class);

    private final DetalleAcopioRepository detalleAcopioRepository;

    private final DetalleAcopioMapper detalleAcopioMapper;

    public DetalleAcopioQueryService(DetalleAcopioRepository detalleAcopioRepository, DetalleAcopioMapper detalleAcopioMapper) {
        this.detalleAcopioRepository = detalleAcopioRepository;
        this.detalleAcopioMapper = detalleAcopioMapper;
    }

    /**
     * Return a {@link List} of {@link DetalleAcopioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalleAcopioDTO> findByCriteria(DetalleAcopioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalleAcopio> specification = createSpecification(criteria);
        return detalleAcopioMapper.toDto(detalleAcopioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalleAcopioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalleAcopioDTO> findByCriteria(DetalleAcopioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalleAcopio> specification = createSpecification(criteria);
        return detalleAcopioRepository.findAll(specification, page).map(detalleAcopioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalleAcopioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalleAcopio> specification = createSpecification(criteria);
        return detalleAcopioRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalleAcopioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalleAcopio> createSpecification(DetalleAcopioCriteria criteria) {
        Specification<DetalleAcopio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalleAcopio_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), DetalleAcopio_.date));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DetalleAcopio_.description));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), DetalleAcopio_.quantity));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), DetalleAcopio_.unitPrice));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DetalleAcopio_.amount));
            }
            if (criteria.getRequestDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestDate(), DetalleAcopio_.requestDate));
            }
            if (criteria.getPromiseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromiseDate(), DetalleAcopio_.promiseDate));
            }
            if (criteria.getDeliveryStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryStatus(), DetalleAcopio_.deliveryStatus));
            }
            if (criteria.getAcopioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAcopioId(), root -> root.join(DetalleAcopio_.acopio, JoinType.LEFT).get(Acopio_.id))
                    );
            }
        }
        return specification;
    }
}
