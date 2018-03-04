package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.commands.AddGenreCommand;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.repositories.GenreRepository;
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
