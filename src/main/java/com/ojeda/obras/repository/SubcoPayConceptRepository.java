package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SubcoPayConcept;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubcoPayConcept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcoPayConceptRepository extends JpaRepository<SubcoPayConcept, Long>, JpaSpecificationExecutor<SubcoPayConcept> {
    Optional<SubcoPayConcept> findByConceptoName(String Concepto);
}
