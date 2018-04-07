package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Client;
import pl.com.piotrslowinski.model.Lending;
import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.TimeProvider;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.LendSpecimenCommand;
import pl.com.piotrslowinski.model.repositories.ClientRepository;
import pl.com.piotrslowinski.model.repositories.LendingRepository;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
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
        Specimen specimen = specimenRepository.get(cmd.getCode()).get();
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
