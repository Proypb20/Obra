package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Concepto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Concepto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Long>, JpaSpecificationExecutor<Concepto> {
    Concepto findByName(String name);
}
