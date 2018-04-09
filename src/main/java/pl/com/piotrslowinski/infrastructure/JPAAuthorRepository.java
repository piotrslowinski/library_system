package pl.com.piotrslowinski.infrastructure;

import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPAAuthorRepository implements AuthorRepository {

    private EntityManager entityManager;

    public JPAAuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author get(Integer id) {
        Author author = entityManager.find(Author.class, id);
        if (author == null)
            throw new NoSuchEntityException();
        return author;
    }

    @Override
    public void save(Author author) {
        entityManager.persist(author);
    }

}

