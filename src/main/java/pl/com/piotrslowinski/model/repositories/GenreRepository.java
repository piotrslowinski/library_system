package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.application.GenreDto;
import pl.com.piotrslowinski.model.Genre;


public interface GenreRepository {

    Genre get(Integer id);

    GenreDto getDto(Integer id);

    void save(Genre genre);
}
