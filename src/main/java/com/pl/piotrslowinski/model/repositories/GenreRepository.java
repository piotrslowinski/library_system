package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.application.GenreDto;
import com.pl.piotrslowinski.model.Genre;


public interface GenreRepository {

    Genre get(Integer id);

    GenreDto getDto(Integer id);

    void save(Genre genre);
}
