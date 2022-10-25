package com.ojeda.obras.repository;

import com.ojeda.obras.domain.AdvObraRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdvPendRep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvObraRepRepository extends JpaRepository<AdvObraRep, Long>, JpaSpecificationExecutor<AdvObraRep> {}
