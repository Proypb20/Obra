package com.ojeda.obras.repository;

import com.ojeda.obras.domain.ListaPrecio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ListaPrecio entity.
 */
@Repository
public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Long>, JpaSpecificationExecutor<ListaPrecio> {
    default Optional<ListaPrecio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ListaPrecio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ListaPrecio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct listaPrecio from ListaPrecio listaPrecio left join fetch listaPrecio.proveedor",
        countQuery = "select count(distinct listaPrecio) from ListaPrecio listaPrecio"
    )
    Page<ListaPrecio> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct listaPrecio from ListaPrecio listaPrecio left join fetch listaPrecio.proveedor")
    List<ListaPrecio> findAllWithToOneRelationships();

    @Query("select listaPrecio from ListaPrecio listaPrecio left join fetch listaPrecio.proveedor where listaPrecio.id =:id")
    Optional<ListaPrecio> findOneWithToOneRelationships(@Param("id") Long id);
}
