package com.ojeda.obras.repository;

import com.ojeda.obras.domain.ResObra;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Seguimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResObraRepository extends JpaRepository<ResObra, Long>, JpaSpecificationExecutor<ResObra> {
    List<ResObra> findAllByObraName(String obraName);
}
