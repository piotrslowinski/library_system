package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Author;
import com.pl.piotrslowinski.model.commands.AddAuthorCommand;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.repositories.AuthorRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 07.01.2018.
 */
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