package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Genre;

import java.util.List;

/**
 * Created by user on 07.01.2018.
 */
public interface GenreFinder {

    GenreDto getDto(Integer genreId);

    Genre get(Integer genreId);

    List<GenreDto> getAll();
}
