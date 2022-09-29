package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Subcontratista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SubcontratistaRepositoryWithBagRelationships {
    Optional<Subcontratista> fetchBagRelationships(Optional<Subcontratista> subcontratista);

    List<Subcontratista> fetchBagRelationships(List<Subcontratista> subcontratistas);

    Page<Subcontratista> fetchBagRelationships(Page<Subcontratista> subcontratistas);
}
