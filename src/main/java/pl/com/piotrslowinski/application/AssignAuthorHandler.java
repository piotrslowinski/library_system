package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.commands.AssignAuthorCommand;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.repositories.AuthorRepository;
import pl.com.piotrslowinski.model.repositories.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AssignAuthorHandler implements Handler<AssignAuthorCommand> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AssignAuthorHandler(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void handle(AssignAuthorCommand cmd) {
        Book book = bookRepository.get(cmd.getBookId());
        book.assignAuthor(authorRepository.get(cmd.getAuthorId()));
        bookRepository.save(book);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AssignAuthorCommand.class;
    }
}
