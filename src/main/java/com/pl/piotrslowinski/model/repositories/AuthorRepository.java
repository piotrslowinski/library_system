package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Author;

/**
 * Created by user on 07.01.2018.
 */
public interface AuthorRepository {

    Author get(Integer id);

    void save(Author author);

}
