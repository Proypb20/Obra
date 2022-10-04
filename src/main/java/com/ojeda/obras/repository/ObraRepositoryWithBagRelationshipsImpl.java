package com.ojeda.obras.repository;

import com.ojeda.obras.domain.Obra;
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
public class ObraRepositoryWithBagRelationshipsImpl implements ObraRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Obra> fetchBagRelationships(Optional<Obra> obra) {
        return obra.map(this::fetchSubcontratistas).map(this::fetchClientes);
    }

    @Override
    public Page<Obra> fetchBagRelationships(Page<Obra> obras) {
        return new PageImpl<>(fetchBagRelationships(obras.getContent()), obras.getPageable(), obras.getTotalElements());
    }

    @Override
    public List<Obra> fetchBagRelationships(List<Obra> obras) {
        return Optional.of(obras).map(this::fetchSubcontratistas).map(this::fetchClientes).orElse(Collections.emptyList());
    }

    Obra fetchSubcontratistas(Obra result) {
        return entityManager
            .createQuery("select obra from Obra obra left join fetch obra.subcontratistas where obra is :obra", Obra.class)
            .setParameter("obra", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Obra> fetchSubcontratistas(List<Obra> obras) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, obras.size()).forEach(index -> order.put(obras.get(index).getId(), index));
        List<Obra> result = entityManager
            .createQuery("select distinct obra from Obra obra left join fetch obra.subcontratistas where obra in :obras", Obra.class)
            .setParameter("obras", obras)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Obra fetchClientes(Obra result) {
        return entityManager
            .createQuery("select obra from Obra obra left join fetch obra.clientes where obra is :obra", Obra.class)
            .setParameter("obra", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Obra> fetchClientes(List<Obra> obras) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, obras.size()).forEach(index -> order.put(obras.get(index).getId(), index));
        List<Obra> result = entityManager
            .createQuery("select distinct obra from Obra obra left join fetch obra.clientes where obra in :obras", Obra.class)
            .setParameter("obras", obras)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
