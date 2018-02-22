package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.application.AuthorDto;
import com.pl.piotrslowinski.application.AuthorFinder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;


@Component
public class JPAAuthorFinder implements AuthorFinder {

    private EntityManager entityManager;

    public JPAAuthorFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> results = entityManager.createQuery("SELECT NEW com.pl.piotrslowinski.application.AuthorDto(a.id, a.firstName, a.lastName) FROM Author a").getResultList();
        return results;
    }
}
