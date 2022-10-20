package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.repository.AcopioRepository;
import com.ojeda.obras.service.criteria.AcopioCriteria;
import com.ojeda.obras.service.dto.AcopioDTO;
import com.ojeda.obras.service.mapper.AcopioMapper;
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
 * Service for executing complex queries for {@link Acopio} entities in the database.
 * The main input is a {@link AcopioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcopioDTO} or a {@link Page} of {@link AcopioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcopioQueryService extends QueryService<Acopio> {

    private final Logger log = LoggerFactory.getLogger(AcopioQueryService.class);

    private final AcopioRepository acopioRepository;

    private final AcopioMapper acopioMapper;

    public AcopioQueryService(AcopioRepository acopioRepository, AcopioMapper acopioMapper) {
        this.acopioRepository = acopioRepository;
        this.acopioMapper = acopioMapper;
    }

    /**
     * Return a {@link List} of {@link AcopioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcopioDTO> findByCriteria(AcopioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Acopio> specification = createSpecification(criteria);
        return acopioMapper.toDto(acopioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AcopioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcopioDTO> findByCriteria(AcopioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Acopio> specification = createSpecification(criteria);
        return acopioRepository.findAll(specification, page).map(acopioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcopioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Acopio> specification = createSpecification(criteria);
        return acopioRepository.count(specification);
    }

    /**
     * Function to convert {@link AcopioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Acopio> createSpecification(AcopioCriteria criteria) {
        Specification<Acopio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Acopio_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Acopio_.date));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Acopio_.totalAmount));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Acopio_.obra, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getListaprecioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getListaprecioId(),
                            root -> root.join(Acopio_.listaprecio, JoinType.LEFT).get(ListaPrecio_.id)
                        )
                    );
            }
            if (criteria.getProveedorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProveedorId(),
                            root -> root.join(Acopio_.proveedor, JoinType.LEFT).get(Proveedor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
