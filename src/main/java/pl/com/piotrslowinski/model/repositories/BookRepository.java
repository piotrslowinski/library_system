package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.model.Book;


public interface BookRepository {

    Book get(Integer id);

    void save(Book book);

}

