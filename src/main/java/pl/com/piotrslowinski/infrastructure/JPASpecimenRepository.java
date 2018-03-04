package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPASpecimenRepository implements SpecimenRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Specimen specimen) {
        entityManager.persist(specimen);
    }

    @Override
    public Specimen get(String code) {

        Specimen specimen = (Specimen) entityManager.createQuery("FROM Specimen s WHERE s.code = :code")
                .setParameter("code", code)
                .getSingleResult();
        return specimen;
    }

    @Override
    public void remove(String code) {
        Specimen specimen = (Specimen) entityManager.createQuery("FROM Specimen s WHERE s.code = :code")
                .setParameter("code", code)
                .getSingleResult();
        entityManager.remove(specimen);
    }
}
