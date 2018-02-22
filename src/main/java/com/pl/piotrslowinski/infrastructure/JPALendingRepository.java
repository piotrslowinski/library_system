package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.Lending;
import com.pl.piotrslowinski.model.Specimen;
import com.pl.piotrslowinski.model.repositories.LendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPALendingRepository implements LendingRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Lending lending) {
        entityManager.persist(lending);
    }

    @Override
    public Lending get(Specimen specimen) {
        Lending lending = (Lending) entityManager.createQuery("FROM Lending l WHERE l.specimen = :specimen")
                .setParameter("specimen", specimen)
                .getSingleResult();
        return lending;
    }
}
