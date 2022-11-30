package com.ojeda.obras.repository;

import com.ojeda.obras.domain.BalanceRep;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Balancerep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceRepRepository extends JpaRepository<BalanceRep, Long>, JpaSpecificationExecutor<BalanceRep> {}
