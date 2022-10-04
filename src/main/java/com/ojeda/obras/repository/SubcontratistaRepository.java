package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Subcontratista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subcontratista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcontratistaRepository extends JpaRepository<Subcontratista, Long>, JpaSpecificationExecutor<Subcontratista> {}
