package com.ojeda.obras.repository;

import com.ojeda.obras.domain.DetalleListaPrecio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalleListaPrecio entity.
 */
@Repository
public interface DetalleListaPrecioRepository
    extends JpaRepository<DetalleListaPrecio, Long>, JpaSpecificationExecutor<DetalleListaPrecio> {
    default Optional<DetalleListaPrecio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DetalleListaPrecio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DetalleListaPrecio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct detalleListaPrecio from DetalleListaPrecio detalleListaPrecio left join fetch detalleListaPrecio.listaPrecio",
        countQuery = "select count(distinct detalleListaPrecio) from DetalleListaPrecio detalleListaPrecio"
    )
    Page<DetalleListaPrecio> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct detalleListaPrecio from DetalleListaPrecio detalleListaPrecio left join fetch detalleListaPrecio.listaPrecio")
    List<DetalleListaPrecio> findAllWithToOneRelationships();

    @Query(
        "select detalleListaPrecio from DetalleListaPrecio detalleListaPrecio left join fetch detalleListaPrecio.listaPrecio where detalleListaPrecio.id =:id"
    )
    Optional<DetalleListaPrecio> findOneWithToOneRelationships(@Param("id") Long id);
}
