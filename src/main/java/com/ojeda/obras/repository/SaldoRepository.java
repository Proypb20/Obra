package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Saldo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long>, JpaSpecificationExecutor<Saldo> {}
