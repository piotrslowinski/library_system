package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Book;


public interface BookRepository {

    Book get(Integer id);

    void save(Book book);

}

