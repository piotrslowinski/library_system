package pl.com.piotrslowinski.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.commands.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddNewAndDeleteSpecimenTest extends AcceptanceTest {

    @Autowired
    private AddNewSpecimenHandler addNewSpecimenHandler;

    @Autowired
    private AddGenreHandler addGenreHandler;

    @Autowired
    private AddAuthorHandler addAuthorHandler;

    @Autowired
    private CreateBookHandler createBookHandler;

    @Autowired
    private DeleteSpecimenHandler deleteSpecimenHandler;

    @Autowired
    private  BookFinder bookFinder;

    private AddNewSpecimenCommand addNewSpecimenCommand;

    private CreateBookCommand createBookCommand;

    private AddGenreCommand addGenreCommand;

    private AddAuthorCommand addAuthorCommand;


    @Transactional
    public void addGenre(String genre) {
        addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName(genre);
        addGenreHandler.handle(addGenreCommand);
    }

    public void addAuthor(String firstName, String lastName) {
        addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName(firstName);
        addAuthorCommand.setLastName(lastName);
        addAuthorHandler.handle(addAuthorCommand);
    }

    private void createBook(String title, String isbn, String publishedAt, int genrId, int authorId) {
        createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle(title);
        createBookCommand.setIsbn(isbn);
        createBookCommand.setPublishedAt(LocalDate.parse(publishedAt));
        createBookCommand.setGenreId(genrId);
        createBookCommand.setAuthorId(authorId);
        createBookHandler.handle(createBookCommand);
    }

    private void addSpecimen(int bookId, String code) {
        addNewSpecimenCommand = new AddNewSpecimenCommand();
        addNewSpecimenCommand.setBookId(bookId);
        addNewSpecimenCommand.setCode(code);
        addNewSpecimenHandler.handle(addNewSpecimenCommand);
    }

    private void deleteSpecimen(int bookId, String code) {
        DeleteSpecimenCommand deleteSpecimenCommand = new DeleteSpecimenCommand();
        deleteSpecimenCommand.setBookId(bookId);
        deleteSpecimenCommand.setCode(code);
        deleteSpecimenHandler.handle(deleteSpecimenCommand);
    }


    @Test
    public void shouldAddSpecimen() {
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        createBook("Koran", "abc123", "1999-01-01", 1, 1);

        //when
        addSpecimen(1,"code");

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(Arrays.asList("code"), bookDto.getSpecimensCode().stream().collect(Collectors.toList()));
        assertEquals(1, bookFinder.getAll().size());
    }




    @Test
    public void shouldAddMoreSpecimen() {
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        createBook("Koran", "abc123", "1999-01-01", 1, 1);

        //when
        addSpecimen(1,"code");
        addSpecimen(1,"code2");

        //then

        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(Arrays.asList("code", "code2"), bookDto.getSpecimensCode().stream().collect(Collectors.toList()));
        assertEquals(2, bookDto.getSpecimensCode().size());

    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAddSameSpecimenTwice(){
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        createBook("Koran", "abc123", "1999-01-01", 1, 1);

        //when
        addSpecimen(1,"code");
        addSpecimen(1,"code");
    }

    @Test
    public void shouldDeleteSpecimen(){
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        createBook("Koran", "abc123", "1999-01-01", 1, 1);
        addSpecimen(1,"code");
        addSpecimen(1,"code2");

        //when
        deleteSpecimen(1, "code2");

        //then

        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(Arrays.asList("code"), bookDto.getSpecimensCode().stream().collect(Collectors.toList()));
        assertEquals(1, bookDto.getSpecimensCode().size());
    }


    @Test(expected = CommandInvalidException.class)
    public void shouldThrowErrorWhileTryingToDeleteNonExistSpecimen(){
        //when
        deleteSpecimen(1, "code2");
    }
}
