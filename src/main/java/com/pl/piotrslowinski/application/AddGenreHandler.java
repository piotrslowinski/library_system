package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.commands.AddGenreCommand;
import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.repositories.GenreRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AddGenreHandler implements Handler<AddGenreCommand> {

    private GenreRepository genreRepository;

    public AddGenreHandler(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public void handle(AddGenreCommand cmd) {
        Genre genre = new Genre(cmd.getName());
        genreRepository.save(genre);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddGenreCommand.class;
    }
}
