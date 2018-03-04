package pl.com.piotrslowinski.infrastructure;

import pl.com.piotrslowinski.application.ClientDto;
import pl.com.piotrslowinski.application.ClientFinder;
import pl.com.piotrslowinski.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Component
public class JPAClientFinder implements ClientFinder {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public ClientDto get(Integer clientId) {
        Client client = entityManager.find(Client.class, clientId);
        if (client == null)
            throw new NoSuchEntityException();
        return new ClientDto(client);
    }
}
