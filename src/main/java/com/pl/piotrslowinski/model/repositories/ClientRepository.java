package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Client;

/**
 * Created by user on 07.01.2018.
 */
public interface ClientRepository {

    void save(Client client);

    Client get(Integer clientId);
}
