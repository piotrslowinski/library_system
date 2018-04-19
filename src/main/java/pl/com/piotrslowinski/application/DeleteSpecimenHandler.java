package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Specimen;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.CommandInvalidException;
import pl.com.piotrslowinski.model.commands.DeleteSpecimenCommand;
import pl.com.piotrslowinski.model.commands.ValidationErrors;
import pl.com.piotrslowinski.model.repositories.SpecimenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DeleteSpecimenHandler implements Handler<DeleteSpecimenCommand> {

    private SpecimenRepository specimenRepository;

    public DeleteSpecimenHandler(SpecimenRepository specimenRepository) {
        this.specimenRepository = specimenRepository;
    }

    @Transactional
    @Override
    public void handle(DeleteSpecimenCommand cmd) {
        validateSpecimenPresence(cmd);
        Specimen specimen = specimenRepository.get(cmd.getCode()).get();
//        Book book = bookRepository.get(cmd.getBookId());
        Book book = specimen.getBook();
        book.remove(specimen);
        specimenRepository.remove(cmd.getCode());
    }

    private void validateSpecimenPresence(DeleteSpecimenCommand cmd) {
        if (!specimenRepository.isSpecimenPresent(cmd.getCode())) {
            ValidationErrors errors = new ValidationErrors();
            errors.add("specimen", "specimen with given code doesn't exist");
            throw new CommandInvalidException(errors);
        }
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return DeleteSpecimenCommand.class;
    }
}
