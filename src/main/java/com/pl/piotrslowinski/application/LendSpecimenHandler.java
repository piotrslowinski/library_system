package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Client;
import com.pl.piotrslowinski.model.Lending;
import com.pl.piotrslowinski.model.Specimen;
import com.pl.piotrslowinski.model.TimeProvider;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.commands.LendSpecimenCommand;
import com.pl.piotrslowinski.model.repositories.ClientRepository;
import com.pl.piotrslowinski.model.repositories.LendingRepository;
import com.pl.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class LendSpecimenHandler implements Handler<LendSpecimenCommand>{

    private ClientRepository clientRepository;
    private TimeProvider timeProvider;
    private SpecimenRepository specimenRepository;
    private LendingRepository lendingRepository;

    public LendSpecimenHandler(ClientRepository clientRepository,
                               TimeProvider timeProvider, SpecimenRepository specimenRepository, LendingRepository lendingRepository) {
        this.clientRepository = clientRepository;
        this.timeProvider = timeProvider;
        this.specimenRepository = specimenRepository;
        this.lendingRepository = lendingRepository;
    }


    @Override
    @Transactional
    public void handle(LendSpecimenCommand cmd) {
        Client client = clientRepository.get(cmd.getClientId());
        Specimen specimen = specimenRepository.get(cmd.getCode());
        Lending lending = new Lending(client,specimen,timeProvider);
        client.borrowBook(lending);
        lendingRepository.save(lending);
        clientRepository.save(client);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return LendSpecimenCommand.class;
    }
}
