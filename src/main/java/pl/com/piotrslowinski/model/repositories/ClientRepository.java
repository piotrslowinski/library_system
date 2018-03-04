package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.model.Client;


public interface ClientRepository {

    void save(Client client);

    Client get(Integer clientId);
}
