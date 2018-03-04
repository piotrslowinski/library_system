package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Client;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.RegisterClientCommand;
import pl.com.piotrslowinski.model.repositories.ClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class RegisterClientHandler implements Handler<RegisterClientCommand> {

    private ClientRepository repository;

    public RegisterClientHandler(ClientRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public void handle(RegisterClientCommand cmd) {
        Client client = new Client(cmd.getFirstName(),
                cmd.getLastName(),
                cmd.getDocumentNumber(),
                cmd.getPesel(),
                cmd.getStreet(),
                cmd.getCity()
        );
        repository.save(client);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterClientCommand.class;
    }
}

