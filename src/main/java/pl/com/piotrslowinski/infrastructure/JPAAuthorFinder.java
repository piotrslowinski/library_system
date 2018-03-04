package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.application.AuthorDto;
import pl.com.piotrslowinski.application.AuthorFinder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;


@Component
public class JPAAuthorFinder implements AuthorFinder {

    private EntityManager entityManager;

    public JPAAuthorFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> results = entityManager.createQuery("SELECT NEW AuthorDto(a.id, a.firstName, a.lastName) FROM Author a").getResultList();
        return results;
    }
}
