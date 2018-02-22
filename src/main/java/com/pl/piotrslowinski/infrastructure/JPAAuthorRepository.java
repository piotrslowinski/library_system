package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.Author;
import com.pl.piotrslowinski.model.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPAAuthorRepository implements AuthorRepository {

    private EntityManager entityManager;

    public JPAAuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author get(Integer id) {
        Author author = entityManager.find(Author.class, id);
        if (author == null)
            throw new NoSuchEntityException();
        return author;
    }

    @Override
    public void save(Author author) {
        entityManager.persist(author);
    }

}

