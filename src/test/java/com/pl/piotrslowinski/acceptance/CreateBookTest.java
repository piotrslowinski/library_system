package com.pl.piotrslowinski.acceptance;

import com.pl.piotrslowinski.application.*;
import com.pl.piotrslowinski.model.Author;
import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.commands.AddAuthorCommand;
import com.pl.piotrslowinski.model.commands.AddGenreCommand;
import com.pl.piotrslowinski.model.commands.AddNewSpecimenCommand;
import com.pl.piotrslowinski.model.commands.CreateBookCommand;
import com.pl.piotrslowinski.model.repositories.AuthorRepository;
import com.pl.piotrslowinski.model.repositories.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateBookTest extends AcceptanceTest {

    @Autowired
    private CreateBookHandler createBookHandler;

    @Autowired
    private AddAuthorHandler addAuthorHandler;

    @Autowired
    private AddGenreHandler addGenreHandler;

    @Autowired
    private AddNewSpecimenHandler addNewSpecimenHandler;

    @Autowired
    private BookFinder bookFinder;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AddGenreCommand addGenreCommand;

    @Test
    public void shouldAddGenre(){
        //given
        addGenreCommand.setName("fiction");
        addGenreHandler.handle(addGenreCommand);
        //then
        Genre genre = genreRepository.get(1);
        assertEquals("fiction", genre.getName());

    }


    @Test
    public void shouldCreateAuthor(){
        //given
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Jan");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);

        //then
        Author author = authorRepository.get(1);
        assertEquals("Jan", author.getFirstName());
    }

    @Test
    public void shouldCreateBook(){
        //given
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName("fiction");
        addGenreHandler.handle(addGenreCommand);
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Jan");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);

        //when
        CreateBookCommand createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle("Akuku");
        createBookCommand.setIsbn("abc123");
        createBookCommand.setPublishedAt(LocalDate.parse("1999-01-01"));
        createBookCommand.setGenreId(1);
        createBookCommand.setAuthorId(1);
        createBookHandler.handle(createBookCommand);

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals("Akuku", bookDto.getTitle());
        assertEquals("fiction",bookDto.getGenre().getName());
        assertEquals(Arrays.asList("Nowak"), bookDto.getAuthors().stream().
                map(Author::getLastName).collect(Collectors.toList()));
    }

    @Test
    public void shouldAddSpecimen() {
        //given
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName("fiction");
        addGenreHandler.handle(addGenreCommand);
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Jan");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);

        //when
        CreateBookCommand createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle("Akuku");
        createBookCommand.setIsbn("abc123");
        createBookCommand.setPublishedAt(LocalDate.parse("1999-01-01"));
        createBookCommand.setGenreId(1);
        createBookCommand.setAuthorId(1);
        createBookHandler.handle(createBookCommand);

        AddNewSpecimenCommand addNewSpecimenCommand = new AddNewSpecimenCommand();
        addNewSpecimenCommand.setBookId(1);
        addNewSpecimenCommand.setCode("code");
        addNewSpecimenHandler.handle(addNewSpecimenCommand);

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(Arrays.asList("code"), bookDto.getSpecimensCode().stream().collect(Collectors.toList()));

    }

    @Test
    public void shouldAddMoreSpecimen() {
        //given
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName("fiction");
        addGenreHandler.handle(addGenreCommand);
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Jan");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);

        //when
        CreateBookCommand createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle("Akuku");
        createBookCommand.setIsbn("abc123");
        createBookCommand.setPublishedAt(LocalDate.parse("1999-01-01"));
        createBookCommand.setGenreId(1);
        createBookCommand.setAuthorId(1);
        createBookHandler.handle(createBookCommand);

        AddNewSpecimenCommand addNewSpecimenCommand = new AddNewSpecimenCommand();
        addNewSpecimenCommand.setBookId(1);
        addNewSpecimenCommand.setCode("code");
        addNewSpecimenHandler.handle(addNewSpecimenCommand);

        addNewSpecimenCommand.setBookId(1);
        addNewSpecimenCommand.setCode("code2");
        addNewSpecimenHandler.handle(addNewSpecimenCommand);

        //then

        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(Arrays.asList("code","code2"), bookDto.getSpecimensCode().stream().collect(Collectors.toList()));

    }
}
