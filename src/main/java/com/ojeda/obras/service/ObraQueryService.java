package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Obra;
import com.ojeda.obras.repository.ObraRepository;
import com.ojeda.obras.service.criteria.ObraCriteria;
import com.ojeda.obras.service.dto.ObraDTO;
import com.ojeda.obras.service.mapper.ObraMapper;
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
 * Service for executing complex queries for {@link Obra} entities in the database.
 * The main input is a {@link ObraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObraDTO} or a {@link Page} of {@link ObraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObraQueryService extends QueryService<Obra> {

    private final Logger log = LoggerFactory.getLogger(ObraQueryService.class);

    private final ObraRepository obraRepository;

    private final ObraMapper obraMapper;

    public ObraQueryService(ObraRepository obraRepository, ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.obraMapper = obraMapper;
    }

    /**
     * Return a {@link List} of {@link ObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObraDTO> findByCriteria(ObraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraMapper.toDto(obraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObraDTO> findByCriteria(ObraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraRepository.findAll(specification, page).map(obraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraRepository.count(specification);
    }

    /**
     * Function to convert {@link ObraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Obra> createSpecification(ObraCriteria criteria) {
        Specification<Obra> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Obra_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Obra_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Obra_.address));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Obra_.city));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Obra_.comments));
            }
            if (criteria.getProvinciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProvinciaId(), root -> root.join(Obra_.provincia, JoinType.LEFT).get(Provincia_.id))
                    );
            }
            if (criteria.getSubcontratistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubcontratistaId(),
                            root -> root.join(Obra_.subcontratistas, JoinType.LEFT).get(Subcontratista_.id)
                        )
                    );
            }
            if (criteria.getClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClienteId(), root -> root.join(Obra_.clientes, JoinType.LEFT).get(Cliente_.id))
                    );
            }
        }
        return specification;
    }
}
