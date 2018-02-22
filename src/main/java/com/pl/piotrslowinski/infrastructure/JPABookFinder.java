package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.application.BookDto;
import com.pl.piotrslowinski.application.BookFinder;
import com.pl.piotrslowinski.application.DetailedBookDto;
import com.pl.piotrslowinski.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Component
public class JPABookFinder implements BookFinder {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<BookDto> getAll() {
        List<BookDto> results = entityManager.createQuery("SELECT NEW com.pl.piotrslowinski.application.BookDto(" +
                "b.id, b.title, b.isbn) FROM Book b").getResultList();
        return results;
    }

    @Override
    @Transactional
    public DetailedBookDto getBookDetails(Integer bookId) {
        Book book = entityManager.find(Book.class, bookId);
        if(book == null)
            throw new NoSuchEntityException();
        return new DetailedBookDto(book);
    }
}
