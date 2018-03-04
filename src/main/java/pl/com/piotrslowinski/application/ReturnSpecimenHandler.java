package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Client;
import pl.com.piotrslowinski.model.Lending;
import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.ReturnSpecimenCommand;
import pl.com.piotrslowinski.model.repositories.ClientRepository;
import pl.com.piotrslowinski.model.repositories.LendingRepository;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
