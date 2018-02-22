package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.repositories.BookRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;


@Component
public class JPABookRepository implements BookRepository {


    private EntityManager entityManager;

    public JPABookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book get(Integer id) {
        Book book = entityManager.find(Book.class, id);
        if(book == null)
            throw new NoSuchElementException();
        return book;
    }



    @Override
    public void save(Book book) {
        entityManager.persist(book);
    }
}
