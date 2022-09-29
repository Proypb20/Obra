package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.ListaPrecio;
import com.ojeda.obras.repository.ListaPrecioRepository;
import com.ojeda.obras.service.criteria.ListaPrecioCriteria;
import com.ojeda.obras.service.dto.ListaPrecioDTO;
import com.ojeda.obras.service.mapper.ListaPrecioMapper;
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
 * Service for executing complex queries for {@link ListaPrecio} entities in the database.
 * The main input is a {@link ListaPrecioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ListaPrecioDTO} or a {@link Page} of {@link ListaPrecioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ListaPrecioQueryService extends QueryService<ListaPrecio> {

    private final Logger log = LoggerFactory.getLogger(ListaPrecioQueryService.class);

    private final ListaPrecioRepository listaPrecioRepository;

    private final ListaPrecioMapper listaPrecioMapper;

    public ListaPrecioQueryService(ListaPrecioRepository listaPrecioRepository, ListaPrecioMapper listaPrecioMapper) {
        this.listaPrecioRepository = listaPrecioRepository;
        this.listaPrecioMapper = listaPrecioMapper;
    }

    /**
     * Return a {@link List} of {@link ListaPrecioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ListaPrecioDTO> findByCriteria(ListaPrecioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ListaPrecio> specification = createSpecification(criteria);
        return listaPrecioMapper.toDto(listaPrecioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ListaPrecioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ListaPrecioDTO> findByCriteria(ListaPrecioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ListaPrecio> specification = createSpecification(criteria);
        return listaPrecioRepository.findAll(specification, page).map(listaPrecioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ListaPrecioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ListaPrecio> specification = createSpecification(criteria);
        return listaPrecioRepository.count(specification);
    }

    /**
     * Function to convert {@link ListaPrecioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ListaPrecio> createSpecification(ListaPrecioCriteria criteria) {
        Specification<ListaPrecio> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ListaPrecio_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ListaPrecio_.name));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ListaPrecio_.date));
            }
            if (criteria.getProveedorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProveedorId(),
                            root -> root.join(ListaPrecio_.proveedor, JoinType.LEFT).get(Proveedor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
