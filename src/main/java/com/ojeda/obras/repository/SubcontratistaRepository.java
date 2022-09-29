package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Subcontratista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subcontratista entity.
 *
 * When extending this class, extend SubcontratistaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SubcontratistaRepository
    extends SubcontratistaRepositoryWithBagRelationships, JpaRepository<Subcontratista, Long>, JpaSpecificationExecutor<Subcontratista> {
    default Optional<Subcontratista> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Subcontratista> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Subcontratista> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
