package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;


@Component
public class JPASpecimenRepository implements SpecimenRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Specimen specimen) {
        entityManager.persist(specimen);
    }

    @Override
    public Optional<Specimen> get(String code) {
        try {
            Specimen specimen = (Specimen) entityManager.createQuery("FROM Specimen s WHERE s.code = :code")
                    .setParameter("code", code)
                    .getSingleResult();
            return Optional.of(specimen);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(String code) {
        Specimen specimen = (Specimen) entityManager.createQuery("FROM Specimen s WHERE s.code = :code")
                .setParameter("code", code)
                .getSingleResult();
        entityManager.remove(specimen);
    }

    @Override
    public boolean isSpecimenPresent(String code) {
        return get(code).isPresent();
    }
}
