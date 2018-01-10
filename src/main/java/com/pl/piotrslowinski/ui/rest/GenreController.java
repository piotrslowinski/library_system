package com.pl.piotrslowinski.ui.rest;

import com.pl.piotrslowinski.application.CommandGateway;
import com.pl.piotrslowinski.application.GenreDto;
import com.pl.piotrslowinski.application.GenreFinder;
import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.commands.AddGenreCommand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 08.01.2018.
 */
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

    @PutMapping
    public void addGenre(@RequestBody AddGenreCommand cmd){
        commandGateway.execute(cmd);
    }
}
