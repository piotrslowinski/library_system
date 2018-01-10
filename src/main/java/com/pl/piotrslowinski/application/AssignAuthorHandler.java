package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.commands.AssignAuthorCommand;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.repositories.AuthorRepository;
import com.pl.piotrslowinski.model.repositories.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 08.01.2018.
 */
@Component
public class AssignAuthorHandler implements Handler<AssignAuthorCommand>{

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
