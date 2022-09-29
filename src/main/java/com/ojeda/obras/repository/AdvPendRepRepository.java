package com.ojeda.obras.repository;

import com.ojeda.obras.domain.AdvPendRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdvPendRep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvPendRepRepository extends JpaRepository<AdvPendRep, Long>, JpaSpecificationExecutor<AdvPendRep> {}
