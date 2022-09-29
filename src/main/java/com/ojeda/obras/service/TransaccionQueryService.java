package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Transaccion;
import com.ojeda.obras.repository.TransaccionRepository;
import com.ojeda.obras.service.criteria.TransaccionCriteria;
import com.ojeda.obras.service.dto.TransaccionDTO;
import com.ojeda.obras.service.mapper.TransaccionMapper;
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
 * Service for executing complex queries for {@link Transaccion} entities in the database.
 * The main input is a {@link TransaccionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransaccionDTO} or a {@link Page} of {@link TransaccionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransaccionQueryService extends QueryService<Transaccion> {

    private final Logger log = LoggerFactory.getLogger(TransaccionQueryService.class);

    private final TransaccionRepository transaccionRepository;

    private final TransaccionMapper transaccionMapper;

    public TransaccionQueryService(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
    }

    /**
     * Return a {@link List} of {@link TransaccionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransaccionDTO> findByCriteria(TransaccionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transaccion> specification = createSpecification(criteria);
        return transaccionMapper.toDto(transaccionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransaccionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransaccionDTO> findByCriteria(TransaccionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transaccion> specification = createSpecification(criteria);
        return transaccionRepository.findAll(specification, page).map(transaccionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransaccionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transaccion> specification = createSpecification(criteria);
        return transaccionRepository.count(specification);
    }

    /**
     * Function to convert {@link TransaccionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transaccion> createSpecification(TransaccionCriteria criteria) {
        Specification<Transaccion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transaccion_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Transaccion_.date));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethod(), Transaccion_.paymentMethod));
            }
            if (criteria.getTransactionNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTransactionNumber(), Transaccion_.transactionNumber));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Transaccion_.amount));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Transaccion_.note));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Transaccion_.obra, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getSubcontratistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubcontratistaId(),
                            root -> root.join(Transaccion_.subcontratista, JoinType.LEFT).get(Subcontratista_.id)
                        )
                    );
            }
            if (criteria.getTipoComprobanteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoComprobanteId(),
                            root -> root.join(Transaccion_.tipoComprobante, JoinType.LEFT).get(TipoComprobante_.id)
                        )
                    );
            }
            if (criteria.getConceptoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConceptoId(),
                            root -> root.join(Transaccion_.concepto, JoinType.LEFT).get(Concepto_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
