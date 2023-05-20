package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Concepto;
import com.ojeda.obras.domain.Movimiento;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Movimiento entity.
 */
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {
    default Optional<Movimiento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Movimiento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Movimiento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista left join fetch movimiento.concepto left join fetch movimiento.tipoComprobante",
        countQuery = "select count(distinct movimiento) from Movimiento movimiento"
    )
    Page<Movimiento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista left join fetch movimiento.concepto left join fetch movimiento.tipoComprobante"
    )
    List<Movimiento> findAllWithToOneRelationships();

    @Query(
        "select movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista left join fetch movimiento.concepto left join fetch movimiento.tipoComprobante where movimiento.id =:id"
    )
    Optional<Movimiento> findOneWithToOneRelationships(@Param("id") Long id);

    List<Movimiento> findAllByConceptoAndDateBetween(Concepto concepto, Instant fromDate, Instant toDate);

    @Query("SELECT COUNT(1) from Movimiento movimiento where movimiento.obra.id = :id ")
    Long getCountByObraId(@Param("id") Long id);

    @Query("SELECT COUNT(1) from Movimiento movimiento where movimiento.subcontratista.id = :id ")
    Long getCountBySubcontratistaId(@Param("id") Long id);

    @Query("SELECT COUNT(1) from Movimiento movimiento where movimiento.concepto.id = :id ")
    Long getCountByConceptoId(@Param("id") Long id);
}
