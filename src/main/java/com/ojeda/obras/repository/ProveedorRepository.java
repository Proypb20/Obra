package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Proveedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Proveedor entity.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long>, JpaSpecificationExecutor<Proveedor> {
    default Optional<Proveedor> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Proveedor> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Proveedor> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct proveedor from Proveedor proveedor left join fetch proveedor.provincia",
        countQuery = "select count(distinct proveedor) from Proveedor proveedor"
    )
    Page<Proveedor> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct proveedor from Proveedor proveedor left join fetch proveedor.provincia")
    List<Proveedor> findAllWithToOneRelationships();

    @Query("select proveedor from Proveedor proveedor left join fetch proveedor.provincia where proveedor.id =:id")
    Optional<Proveedor> findOneWithToOneRelationships(@Param("id") Long id);
}
