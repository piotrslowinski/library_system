package pl.com.piotrslowinski.ui.rest;

import pl.com.piotrslowinski.application.CommandGateway;
import pl.com.piotrslowinski.application.GenreDto;
import pl.com.piotrslowinski.application.GenreFinder;
import pl.com.piotrslowinski.infrastructure.Secured;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.commands.AddGenreCommand;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/genres")
public class GenreController {

    private CommandGateway commandGateway;
    private GenreFinder genreFinder;


    public GenreController(CommandGateway commandGateway, GenreFinder genreFinder) {
        this.commandGateway = commandGateway;
        this.genreFinder = genreFinder;
    }

    @GetMapping
    public List<GenreDto> getGenreList(){
        return genreFinder.getAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable Integer genreId){
        return genreFinder.get(genreId);
    }

    @Secured
    @PutMapping
    public void addGenre(@RequestBody AddGenreCommand cmd){
        commandGateway.execute(cmd);
    }
}
