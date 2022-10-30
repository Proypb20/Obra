package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SubcoPayAmount;
import com.ojeda.obras.service.dto.SubcoPayAmountDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubcoPayAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcoPayAmountRepository extends JpaRepository<SubcoPayAmount, Long>, JpaSpecificationExecutor<SubcoPayAmount> {
    Optional<SubcoPayAmount> findBySubcontratistaAndObraNameAndConceptoName(String subContratista, String obraName, String Concepto);
}
