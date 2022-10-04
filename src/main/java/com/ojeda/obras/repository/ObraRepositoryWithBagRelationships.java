package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Obra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ObraRepositoryWithBagRelationships {
    Optional<Obra> fetchBagRelationships(Optional<Obra> obra);

    List<Obra> fetchBagRelationships(List<Obra> obras);

    Page<Obra> fetchBagRelationships(Page<Obra> obras);
}
