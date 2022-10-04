package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Transaccion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transaccion entity.
 */
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long>, JpaSpecificationExecutor<Transaccion> {
    default Optional<Transaccion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Transaccion> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Transaccion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transaccion from Transaccion transaccion left join fetch transaccion.obra left join fetch transaccion.subcontratista left join fetch transaccion.tipoComprobante left join fetch transaccion.concepto",
        countQuery = "select count(distinct transaccion) from Transaccion transaccion"
    )
    Page<Transaccion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transaccion from Transaccion transaccion left join fetch transaccion.obra left join fetch transaccion.subcontratista left join fetch transaccion.tipoComprobante left join fetch transaccion.concepto"
    )
    List<Transaccion> findAllWithToOneRelationships();

    @Query(
        "select transaccion from Transaccion transaccion left join fetch transaccion.obra left join fetch transaccion.subcontratista left join fetch transaccion.tipoComprobante left join fetch transaccion.concepto where transaccion.id =:id"
    )
    Optional<Transaccion> findOneWithToOneRelationships(@Param("id") Long id);
}
