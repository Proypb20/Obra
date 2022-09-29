package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Subcontratista;
import com.ojeda.obras.repository.SubcontratistaRepository;
import com.ojeda.obras.service.criteria.SubcontratistaCriteria;
import com.ojeda.obras.service.dto.SubcontratistaDTO;
import com.ojeda.obras.service.mapper.SubcontratistaMapper;
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
 * Service for executing complex queries for {@link Subcontratista} entities in the database.
 * The main input is a {@link SubcontratistaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubcontratistaDTO} or a {@link Page} of {@link SubcontratistaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubcontratistaQueryService extends QueryService<Subcontratista> {

    private final Logger log = LoggerFactory.getLogger(SubcontratistaQueryService.class);

    private final SubcontratistaRepository subcontratistaRepository;

    private final SubcontratistaMapper subcontratistaMapper;

    public SubcontratistaQueryService(SubcontratistaRepository subcontratistaRepository, SubcontratistaMapper subcontratistaMapper) {
        this.subcontratistaRepository = subcontratistaRepository;
        this.subcontratistaMapper = subcontratistaMapper;
    }

    /**
     * Return a {@link List} of {@link SubcontratistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubcontratistaDTO> findByCriteria(SubcontratistaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subcontratista> specification = createSpecification(criteria);
        return subcontratistaMapper.toDto(subcontratistaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubcontratistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubcontratistaDTO> findByCriteria(SubcontratistaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subcontratista> specification = createSpecification(criteria);
        return subcontratistaRepository.findAll(specification, page).map(subcontratistaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubcontratistaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subcontratista> specification = createSpecification(criteria);
        return subcontratistaRepository.count(specification);
    }

    /**
     * Function to convert {@link SubcontratistaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Subcontratista> createSpecification(SubcontratistaCriteria criteria) {
        Specification<Subcontratista> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Subcontratista_.id));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Subcontratista_.lastName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Subcontratista_.firstName));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Subcontratista_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Subcontratista_.email));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Subcontratista_.obras, JoinType.LEFT).get(Obra_.id))
                    );
            }
        }
        return specification;
    }
}
