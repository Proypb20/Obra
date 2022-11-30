package com.ojeda.obras.service;

import com.ojeda.obras.domain.BalanceRep;
import com.ojeda.obras.domain.BalanceRep_;
import com.ojeda.obras.repository.BalanceRepRepository;
import com.ojeda.obras.service.criteria.BalanceRepCriteria;
import com.ojeda.obras.service.dto.BalanceRepDTO;
import com.ojeda.obras.service.mapper.BalanceRepMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BalanceRep} entities in the database.
 * The main input is a {@link BalanceRepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BalanceRepDTO} or a {@link Page} of {@link BalanceRepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BalanceRepQueryService extends QueryService<BalanceRep> {

    private final Logger log = LoggerFactory.getLogger(BalanceRepQueryService.class);

    private final BalanceRepRepository balanceRepRepository;

    private final BalanceRepMapper balanceRepMapper;

    public BalanceRepQueryService(BalanceRepRepository balanceRepRepository, BalanceRepMapper balanceRepMapper) {
        this.balanceRepRepository = balanceRepRepository;
        this.balanceRepMapper = balanceRepMapper;
    }

    /**
     * Return a {@link List} of {@link BalanceRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BalanceRepDTO> findByCriteria(BalanceRepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BalanceRep> specification = createSpecification(criteria);
        return balanceRepMapper.toDto(balanceRepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BalanceRepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BalanceRepDTO> findByCriteria(BalanceRepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BalanceRep> specification = createSpecification(criteria);
        return balanceRepRepository.findAll(specification, page).map(balanceRepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BalanceRepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BalanceRep> specification = createSpecification(criteria);
        return balanceRepRepository.count(specification);
    }

    /**
     * Function to convert {@link BalanceRepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BalanceRep> createSpecification(BalanceRepCriteria criteria) {
        Specification<BalanceRep> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BalanceRep_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), BalanceRep_.date));
            }
            if (criteria.getIngreso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIngreso(), BalanceRep_.ingreso));
            }
            if (criteria.getEgreso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEgreso(), BalanceRep_.egreso));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), BalanceRep_.amount));
            }
        }
        return specification;
    }
}
