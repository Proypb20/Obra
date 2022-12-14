package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Acopio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Acopio entity.
 */
@Repository
public interface AcopioRepository extends JpaRepository<Acopio, Long>, JpaSpecificationExecutor<Acopio> {
    default Optional<Acopio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Acopio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Acopio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct acopio from Acopio acopio left join fetch acopio.obra left join fetch acopio.listaprecio left join fetch acopio.proveedor",
        countQuery = "select count(distinct acopio) from Acopio acopio"
    )
    Page<Acopio> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct acopio from Acopio acopio left join fetch acopio.obra left join fetch acopio.listaprecio left join fetch acopio.proveedor"
    )
    List<Acopio> findAllWithToOneRelationships();

    @Query(
        "select acopio from Acopio acopio left join fetch acopio.obra left join fetch acopio.listaprecio left join fetch acopio.proveedor where acopio.id =:id"
    )
    Optional<Acopio> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select COALESCE(sum(detalleAcopio.amount),0) from DetalleAcopio detalleAcopio where detalleAcopio.acopio.id = :id ")
    Double getSumAmount(@Param("id") Long id);

    @Query("select count(1) from Acopio acopio where acopio.proveedor.id = :id ")
    Long getCountByProveedorId(@Param("id") Long id);
}
