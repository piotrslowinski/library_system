package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.commands.AddNewSpecimenCommand;
import pl.com.piotrslowinski.model.commands.Command;
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
