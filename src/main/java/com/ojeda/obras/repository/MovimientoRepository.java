package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Movimiento;
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
        value = "select distinct movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista",
        countQuery = "select count(distinct movimiento) from Movimiento movimiento"
    )
    Page<Movimiento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista"
    )
    List<Movimiento> findAllWithToOneRelationships();

    @Query(
        "select movimiento from Movimiento movimiento left join fetch movimiento.obra left join fetch movimiento.subcontratista where movimiento.id =:id"
    )
    Optional<Movimiento> findOneWithToOneRelationships(@Param("id") Long id);
}
