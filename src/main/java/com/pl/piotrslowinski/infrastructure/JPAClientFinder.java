package com.pl.piotrslowinski.infrastructure;

import com.pl.piotrslowinski.application.ClientDto;
import com.pl.piotrslowinski.application.ClientFinder;
import com.pl.piotrslowinski.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by user on 07.01.2018.
 */
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
