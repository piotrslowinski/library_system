package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Author;
import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.TimeProvider;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.commands.CreateBookCommand;
import com.pl.piotrslowinski.model.repositories.AuthorRepository;
import com.pl.piotrslowinski.model.repositories.BookRepository;
import com.pl.piotrslowinski.model.repositories.GenreRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 07.01.2018.
 */
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