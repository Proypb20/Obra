package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SubcoPayPayment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubcoPayPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcoPayPaymentRepository extends JpaRepository<SubcoPayPayment, Long>, JpaSpecificationExecutor<SubcoPayPayment> {
    Optional<SubcoPayPayment> findBySubcontratistaAndObraName(String subcontratista, String ObraName);
}
