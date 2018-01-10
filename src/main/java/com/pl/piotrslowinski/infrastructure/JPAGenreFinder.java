package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.application.GenreDto;
import com.pl.piotrslowinski.application.GenreFinder;
import com.pl.piotrslowinski.model.Genre;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class JPAGenreFinder implements GenreFinder {

    private EntityManager entityManager;

    public JPAGenreFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public GenreDto getDto(Integer genreId) {
        return null;
    }

    @Override
    public Genre get(Integer genreId) {
        Genre genre = entityManager.find(Genre.class, genreId);
        return genre;
    }

    @Override
    public List<GenreDto> getAll() {
        List<GenreDto> results = entityManager.createQuery("SELECT NEW" +
                " pl.com.piotrslowinski.application.GenreDto(g.id, g.name) FROM Genre g").getResultList();
        return results;
    }
}
