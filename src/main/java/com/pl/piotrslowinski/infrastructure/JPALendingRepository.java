package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.Lending;
import com.pl.piotrslowinski.model.repositories.LendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by user on 08.01.2018.
 */
@Component
public class JPALendingRepository implements LendingRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Lending lending) {
        entityManager.persist(lending);
    }

    @Override
    public Lending get(String code) {
        return null;
    }
}
