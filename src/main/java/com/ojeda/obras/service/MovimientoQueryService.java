package com.ojeda.obras.service;

import com.ojeda.obras.domain.*; // for static metamodels
import com.ojeda.obras.domain.Movimiento;
import com.ojeda.obras.repository.MovimientoRepository;
import com.ojeda.obras.service.criteria.MovimientoCriteria;
import com.ojeda.obras.service.dto.MovimientoDTO;
import com.ojeda.obras.service.mapper.MovimientoMapper;
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
 * Service for executing complex queries for {@link Movimiento} entities in the database.
 * The main input is a {@link MovimientoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MovimientoDTO} or a {@link Page} of {@link MovimientoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MovimientoQueryService extends QueryService<Movimiento> {

    private final Logger log = LoggerFactory.getLogger(MovimientoQueryService.class);

    private final MovimientoRepository movimientoRepository;

    private final MovimientoMapper movimientoMapper;

    public MovimientoQueryService(MovimientoRepository movimientoRepository, MovimientoMapper movimientoMapper) {
        this.movimientoRepository = movimientoRepository;
        this.movimientoMapper = movimientoMapper;
    }

    /**
     * Return a {@link List} of {@link MovimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MovimientoDTO> findByCriteria(MovimientoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Movimiento> specification = createSpecification(criteria);
        return movimientoMapper.toDto(movimientoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MovimientoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MovimientoDTO> findByCriteria(MovimientoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Movimiento> specification = createSpecification(criteria);
        return movimientoRepository.findAll(specification, page).map(movimientoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MovimientoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Movimiento> specification = createSpecification(criteria);
        return movimientoRepository.count(specification);
    }

    /**
     * Function to convert {@link MovimientoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Movimiento> createSpecification(MovimientoCriteria criteria) {
        Specification<Movimiento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Movimiento_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Movimiento_.date));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Movimiento_.description));
            }
            if (criteria.getMetodoPago() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoPago(), Movimiento_.metodoPago));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Movimiento_.amount));
            }
            if (criteria.getTransactionNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionNumber(), Movimiento_.transactionNumber));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Movimiento_.obra, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getSubcontratistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubcontratistaId(),
                            root -> root.join(Movimiento_.subcontratista, JoinType.LEFT).get(Subcontratista_.id)
                        )
                    );
            }
            if (criteria.getConceptoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConceptoId(),
                            root -> root.join(Movimiento_.concepto, JoinType.LEFT).get(Concepto_.id)
                        )
                    );
            }
            if (criteria.getTipoComprobanteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoComprobanteId(),
                            root -> root.join(Movimiento_.tipoComprobante, JoinType.LEFT).get(TipoComprobante_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
