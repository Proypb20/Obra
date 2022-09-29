package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Provincia;
import com.ojeda.obras.repository.ProvinciaRepository;
import com.ojeda.obras.service.criteria.ProvinciaCriteria;
import com.ojeda.obras.service.dto.ProvinciaDTO;
import com.ojeda.obras.service.mapper.ProvinciaMapper;
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
 * Service for executing complex queries for {@link Provincia} entities in the database.
 * The main input is a {@link ProvinciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProvinciaDTO} or a {@link Page} of {@link ProvinciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProvinciaQueryService extends QueryService<Provincia> {

    private final Logger log = LoggerFactory.getLogger(ProvinciaQueryService.class);

    private final ProvinciaRepository provinciaRepository;

    private final ProvinciaMapper provinciaMapper;

    public ProvinciaQueryService(ProvinciaRepository provinciaRepository, ProvinciaMapper provinciaMapper) {
        this.provinciaRepository = provinciaRepository;
        this.provinciaMapper = provinciaMapper;
    }

    /**
     * Return a {@link List} of {@link ProvinciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProvinciaDTO> findByCriteria(ProvinciaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Provincia> specification = createSpecification(criteria);
        return provinciaMapper.toDto(provinciaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProvinciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProvinciaDTO> findByCriteria(ProvinciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Provincia> specification = createSpecification(criteria);
        return provinciaRepository.findAll(specification, page).map(provinciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProvinciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Provincia> specification = createSpecification(criteria);
        return provinciaRepository.count(specification);
    }

    /**
     * Function to convert {@link ProvinciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Provincia> createSpecification(ProvinciaCriteria criteria) {
        Specification<Provincia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Provincia_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Provincia_.name));
            }
        }
        return specification;
    }
}
