package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Client;
import com.pl.piotrslowinski.model.Lending;
import com.pl.piotrslowinski.model.Specimen;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.commands.ReturnSpecimenCommand;
import com.pl.piotrslowinski.model.repositories.ClientRepository;
import com.pl.piotrslowinski.model.repositories.LendingRepository;
import com.pl.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 11.01.2018.
 */
@Component
public class ReturnSpecimenHandler implements Handler<ReturnSpecimenCommand> {

    private ClientRepository clientRepository;
    private SpecimenRepository specimenRepository;
    private LendingRepository lendingRepository;

    public ReturnSpecimenHandler(ClientRepository clientRepository, SpecimenRepository specimenRepository, LendingRepository lendingRepository) {
        this.clientRepository = clientRepository;
        this.specimenRepository = specimenRepository;
        this.lendingRepository = lendingRepository;
    }

    @Override
    @Transactional
    public void handle(ReturnSpecimenCommand cmd) {
        Client client = clientRepository.get(cmd.getClientId());
        Specimen specimen = specimenRepository.get(cmd.getCode());
        Lending lending = lendingRepository.get(specimen);
        client.returnBook(lending);
        lendingRepository.save(lending);
        clientRepository.save(client);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return ReturnSpecimenCommand.class;
    }
}
