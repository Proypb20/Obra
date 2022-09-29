package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Proveedor;
import com.ojeda.obras.repository.ProveedorRepository;
import com.ojeda.obras.service.criteria.ProveedorCriteria;
import com.ojeda.obras.service.dto.ProveedorDTO;
import com.ojeda.obras.service.mapper.ProveedorMapper;
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
 * Service for executing complex queries for {@link Proveedor} entities in the database.
 * The main input is a {@link ProveedorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProveedorDTO} or a {@link Page} of {@link ProveedorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProveedorQueryService extends QueryService<Proveedor> {

    private final Logger log = LoggerFactory.getLogger(ProveedorQueryService.class);

    private final ProveedorRepository proveedorRepository;

    private final ProveedorMapper proveedorMapper;

    public ProveedorQueryService(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
    }

    /**
     * Return a {@link List} of {@link ProveedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProveedorDTO> findByCriteria(ProveedorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Proveedor> specification = createSpecification(criteria);
        return proveedorMapper.toDto(proveedorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProveedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProveedorDTO> findByCriteria(ProveedorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Proveedor> specification = createSpecification(criteria);
        return proveedorRepository.findAll(specification, page).map(proveedorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProveedorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Proveedor> specification = createSpecification(criteria);
        return proveedorRepository.count(specification);
    }

    /**
     * Function to convert {@link ProveedorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Proveedor> createSpecification(ProveedorCriteria criteria) {
        Specification<Proveedor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Proveedor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Proveedor_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Proveedor_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Proveedor_.city));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Proveedor_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Proveedor_.email));
            }
            if (criteria.getProvinciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProvinciaId(),
                            root -> root.join(Proveedor_.provincia, JoinType.LEFT).get(Provincia_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
