package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Client;


public interface ClientRepository {

    void save(Client client);

    Client get(Integer clientId);
}
