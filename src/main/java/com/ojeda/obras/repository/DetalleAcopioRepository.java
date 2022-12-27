package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Acopio;
import com.ojeda.obras.domain.DetalleAcopio;
import com.ojeda.obras.service.dto.DetalleAcopioDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalleAcopio entity.
 */
@Repository
public interface DetalleAcopioRepository extends JpaRepository<DetalleAcopio, Long>, JpaSpecificationExecutor<DetalleAcopio> {
    default Optional<DetalleAcopio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DetalleAcopio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DetalleAcopio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct detalleAcopio from DetalleAcopio detalleAcopio left join fetch detalleAcopio.detalleListaPrecio",
        countQuery = "select count(distinct detalleAcopio) from DetalleAcopio detalleAcopio"
    )
    Page<DetalleAcopio> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct detalleAcopio from DetalleAcopio detalleAcopio left join fetch detalleAcopio.detalleListaPrecio")
    List<DetalleAcopio> findAllWithToOneRelationships();

    @Query(
        "select detalleAcopio from DetalleAcopio detalleAcopio left join fetch detalleAcopio.detalleListaPrecio where detalleAcopio.id =:id"
    )
    Optional<DetalleAcopio> findOneWithToOneRelationships(@Param("id") Long id);

    List<DetalleAcopio> findAllByAcopio(Acopio acopio);

    List<DetalleAcopio> findAllByAcopioId(Long AcopioId);
}
