package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Seguimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Seguimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long>, JpaSpecificationExecutor<Seguimiento> {
    List<Seguimiento> findAllByObraNameAndPeriodNameAndConceptName(String obraName, String periodName, String conceptName);
    List<Seguimiento> findAllByObraName(String obraName);
    List<Seguimiento> findAllByObraNameAndPeriodName(String obraName, String periodName);
}
