package com.ojeda.obras.repository;

import com.ojeda.obras.domain.AdvObraRep;
import com.ojeda.obras.service.dto.AdvObraRepDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdvPendRep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvObraRepRepository extends JpaRepository<AdvObraRep, Long>, JpaSpecificationExecutor<AdvObraRep> {
    List<AdvObraRep> findAllByObraId(Long id);
}
