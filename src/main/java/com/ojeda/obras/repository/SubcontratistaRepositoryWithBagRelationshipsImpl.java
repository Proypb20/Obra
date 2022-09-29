package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Subcontratista;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SubcontratistaRepositoryWithBagRelationshipsImpl implements SubcontratistaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Subcontratista> fetchBagRelationships(Optional<Subcontratista> subcontratista) {
        return subcontratista.map(this::fetchObras);
    }

    @Override
    public Page<Subcontratista> fetchBagRelationships(Page<Subcontratista> subcontratistas) {
        return new PageImpl<>(
            fetchBagRelationships(subcontratistas.getContent()),
            subcontratistas.getPageable(),
            subcontratistas.getTotalElements()
        );
    }

    @Override
    public List<Subcontratista> fetchBagRelationships(List<Subcontratista> subcontratistas) {
        return Optional.of(subcontratistas).map(this::fetchObras).orElse(Collections.emptyList());
    }

    Subcontratista fetchObras(Subcontratista result) {
        return entityManager
            .createQuery(
                "select subcontratista from Subcontratista subcontratista left join fetch subcontratista.obras where subcontratista is :subcontratista",
                Subcontratista.class
            )
            .setParameter("subcontratista", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Subcontratista> fetchObras(List<Subcontratista> subcontratistas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, subcontratistas.size()).forEach(index -> order.put(subcontratistas.get(index).getId(), index));
        List<Subcontratista> result = entityManager
            .createQuery(
                "select distinct subcontratista from Subcontratista subcontratista left join fetch subcontratista.obras where subcontratista in :subcontratistas",
                Subcontratista.class
            )
            .setParameter("subcontratistas", subcontratistas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
