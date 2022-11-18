package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Obra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Obra entity.
 *
 * When extending this class, extend ObraRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ObraRepository extends ObraRepositoryWithBagRelationships, JpaRepository<Obra, Long>, JpaSpecificationExecutor<Obra> {
    default Optional<Obra> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Obra> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Obra> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct obra from Obra obra left join fetch obra.provincia",
        countQuery = "select count(distinct obra) from Obra obra"
    )
    Page<Obra> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct obra from Obra obra left join fetch obra.provincia")
    List<Obra> findAllWithToOneRelationships();

    @Query("select obra from Obra obra left join fetch obra.provincia where obra.id =:id")
    Optional<Obra> findOneWithToOneRelationships(@Param("id") Long id);

    Obra findByName(String name);
}
