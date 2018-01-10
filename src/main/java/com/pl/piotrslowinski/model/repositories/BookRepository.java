package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Book;

/**
 * Created by user on 07.01.2018.
 */
public interface BookRepository {

    Book get(Integer id);

    void save(Book book);

}

