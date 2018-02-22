package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Genre;

import java.util.List;


public interface GenreFinder {

    GenreDto getDto(Integer genreId);

    Genre get(Integer genreId);

    List<GenreDto> getAll();
}
