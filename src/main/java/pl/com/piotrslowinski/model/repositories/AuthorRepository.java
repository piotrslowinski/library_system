package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.model.Author;


public interface AuthorRepository {

    Author get(Integer id);

    void save(Author author);

}
