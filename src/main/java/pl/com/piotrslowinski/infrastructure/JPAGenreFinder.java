package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.application.GenreDto;
import pl.com.piotrslowinski.application.GenreFinder;
import pl.com.piotrslowinski.model.Genre;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;


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
                " GenreDto(g.id, g.name) FROM Genre g").getResultList();
        return results;
    }
}
