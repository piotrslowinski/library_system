package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.commands.AddNewSpecimenCommand;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.CommandInvalidException;
import pl.com.piotrslowinski.model.commands.ValidationErrors;
import pl.com.piotrslowinski.model.repositories.BookRepository;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
        validateSpecimenPresence(cmd);
        Specimen specimen = new Specimen(book, cmd.getCode());
        book.addSpecimen(specimen);
        specimenRepository.save(specimen);
        bookRepository.save(book);
    }

    private void validateSpecimenPresence(AddNewSpecimenCommand cmd) {
        if(specimenRepository.isSpecimenPresent(cmd.getCode())){
            ValidationErrors errors = new ValidationErrors();
            errors.add("specimen", "specimen already exists");
            throw new CommandInvalidException(errors);
        }
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddNewSpecimenCommand.class;
    }
}
