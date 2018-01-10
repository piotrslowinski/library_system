package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.Specimen;
import com.pl.piotrslowinski.model.commands.AddNewSpecimenCommand;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.repositories.BookRepository;
import com.pl.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class AddNewSpecimenHandler implements Handler<AddNewSpecimenCommand> {

    private BookRepository bookRepository;
    private SpecimenRepository specimenRepository;

    public AddNewSpecimenHandler(BookRepository bookRepository, SpecimenRepository specimenRepository) {
        this.bookRepository = bookRepository;
        this.specimenRepository = specimenRepository;
    }

    @Transactional
    public void handle(AddNewSpecimenCommand cmd) {
        Book book = bookRepository.get(cmd.getBookId());
        Specimen specimen = new Specimen(book, cmd.getCode());
        book.addSpecimen(specimen);
        specimenRepository.save(specimen);
        bookRepository.save(book);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddNewSpecimenCommand.class;
    }
}
