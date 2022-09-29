package com.ojeda.obras.repository;

import com.ojeda.obras.domain.UnidadMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long>, JpaSpecificationExecutor<UnidadMedida> {}
