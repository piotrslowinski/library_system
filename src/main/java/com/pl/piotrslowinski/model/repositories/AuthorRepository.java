package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Author;


public interface AuthorRepository {

    Author get(Integer id);

    void save(Author author);

}
