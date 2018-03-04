package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.TimeProvider;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.CreateBookCommand;
import pl.com.piotrslowinski.model.repositories.AuthorRepository;
import pl.com.piotrslowinski.model.repositories.BookRepository;
import pl.com.piotrslowinski.model.repositories.GenreRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CreateBookHandler implements Handler<CreateBookCommand> {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;
    private TimeProvider timeProvider;

    public CreateBookHandler(BookRepository bookRepository, AuthorRepository authorRepository,
                             GenreRepository genreRepository, TimeProvider timeProvider) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.timeProvider = timeProvider;
    }

    @Transactional
    public void handle(CreateBookCommand cmd) {
        Book book = new Book(
                cmd.getTitle(),
                cmd.getIsbn(),
                cmd.getPublishedAt()
        );
        Genre genre = genreRepository.get(cmd.getGenreId());
        book.assignGenre(genre);
        Author author = authorRepository.get(cmd.getAuthorId());
        book.assignAuthor(author);
        bookRepository.save(book);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CreateBookCommand.class;
    }
}