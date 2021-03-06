package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.model.Client;
import pl.com.piotrslowinski.model.repositories.ClientRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class JPAClientRepository implements ClientRepository {

    private EntityManager entityManager;

    public JPAClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Client client) {
        entityManager.persist(client);
    }

    @Override
    public Client get(Integer clientId) {
        Client client = entityManager.find(Client.class, clientId);
        if (client == null)
            throw new NoSuchEntityException();
        return client;
    }


}
