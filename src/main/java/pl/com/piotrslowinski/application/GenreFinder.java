package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Genre;

import java.util.List;


public interface GenreFinder {

    GenreDto getDto(Integer genreId);

    Genre get(Integer genreId);

    List<GenreDto> getAll();
}
