package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.Specimen;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.commands.DeleteSpecimenCommand;
import com.pl.piotrslowinski.model.repositories.BookRepository;
import com.pl.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 10.01.2018.
 */
@Component
public class DeleteSpecimenHandler implements Handler<DeleteSpecimenCommand> {

    private SpecimenRepository specimenRepository;

    public DeleteSpecimenHandler(SpecimenRepository specimenRepository) {
        this.specimenRepository = specimenRepository;
    }

    @Transactional
    @Override
    public void handle(DeleteSpecimenCommand cmd) {
        Specimen specimen = specimenRepository.get(cmd.getCode());
//        Book book = bookRepository.get(cmd.getBookId());
        Book book = specimen.getBook();
        book.remove(specimen);
        specimenRepository.remove(cmd.getCode());
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return DeleteSpecimenCommand.class;
    }
}
