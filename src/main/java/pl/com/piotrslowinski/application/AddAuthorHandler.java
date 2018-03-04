package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.commands.AddAuthorCommand;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.repositories.AuthorRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AddAuthorHandler implements Handler<AddAuthorCommand> {

    private AuthorRepository authorRepository;

    public AddAuthorHandler(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void handle(AddAuthorCommand command) {
        Author author = new Author(command.getFirstName(), command.getLastName());
        authorRepository.save(author);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddAuthorCommand.class;
    }
}