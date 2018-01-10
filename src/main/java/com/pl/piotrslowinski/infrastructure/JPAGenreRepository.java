package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.application.GenreDto;
import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class JPAGenreRepository implements GenreRepository {

    @Autowired
    private EntityManager entityManager;

    public JPAGenreRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Genre get(Integer id) {
//        Genre genre = (Genre)entityManager.createQuery("FROM Genre g WHERE g.id = :id").
//                setParameter("id",id).getSingleResult();
        Genre genre = entityManager.find(Genre.class, id);
        if(genre == null)
            throw new NoSuchEntityException();
        return genre;
    }

    @Override
    public GenreDto getDto(Integer id) {
//        GenreDto genreDto = entityManager.find(Genre.class, id);
        return null;
    }

    @Override
    public void save(Genre genre) {
        entityManager.persist(genre);
    }
}
