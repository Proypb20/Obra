package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SumTrxRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SumTrxRep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SumTrxRepRepository extends JpaRepository<SumTrxRep, Long>, JpaSpecificationExecutor<SumTrxRep> {}
