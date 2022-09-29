package com.ojeda.obras.repository;

import com.ojeda.obras.domain.DetalleAcopio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalleAcopio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleAcopioRepository extends JpaRepository<DetalleAcopio, Long>, JpaSpecificationExecutor<DetalleAcopio> {}
