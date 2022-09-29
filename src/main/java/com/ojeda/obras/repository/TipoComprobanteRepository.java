package com.ojeda.obras.repository;

import com.ojeda.obras.domain.TipoComprobante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoComprobante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoComprobanteRepository extends JpaRepository<TipoComprobante, Long>, JpaSpecificationExecutor<TipoComprobante> {}
