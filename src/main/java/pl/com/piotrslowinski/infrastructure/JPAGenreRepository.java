package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.application.GenreDto;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPAGenreRepository implements GenreRepository {

    @Autowired
    private EntityManager entityManager;

    public JPAGenreRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Genre get(Integer id) {
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
