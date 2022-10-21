package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SumTrxRep;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SumTrxRep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SumTrxRepRepository extends JpaRepository<SumTrxRep, Long>, JpaSpecificationExecutor<SumTrxRep> {
    List<SumTrxRep> findByFechaBetweenAndObraId(Date fechaStart, Date fechaEnd, Long obraId);
}
