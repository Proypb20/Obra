package com.ojeda.obras.repository;

import com.ojeda.obras.domain.SubcoPaySubco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubcoPaySubco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcoPaySubcoRepository extends JpaRepository<SubcoPaySubco, Long>, JpaSpecificationExecutor<SubcoPaySubco> {
    List<SubcoPaySubco> findByObraId(Long ObraId);
}
