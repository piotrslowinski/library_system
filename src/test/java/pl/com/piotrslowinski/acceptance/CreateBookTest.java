package pl.com.piotrslowinski.acceptance;

import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.commands.AddAuthorCommand;
import pl.com.piotrslowinski.model.commands.AddGenreCommand;
import pl.com.piotrslowinski.model.commands.AddNewSpecimenCommand;
import pl.com.piotrslowinski.model.commands.CreateBookCommand;
import pl.com.piotrslowinski.model.repositories.AuthorRepository;
import pl.com.piotrslowinski.model.repositories.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
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


    public void addGenre(String genreName) {
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName(genreName);
        addGenreHandler.handle(addGenreCommand);
    }

    private void addAuthor(String firstName, String lastName) {
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Jan");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);
    }

    private void createBook(String title, String isbn, String publishedAt, int genrId, int authorId) {
        CreateBookCommand createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle(title);
        createBookCommand.setIsbn(isbn);
        createBookCommand.setPublishedAt(LocalDate.parse(publishedAt));
        createBookCommand.setGenreId(genrId);
        createBookCommand.setAuthorId(authorId);
        createBookHandler.handle(createBookCommand);
    }

    @Test
    public void shouldAddGenre() {
        //given
        addGenre("fiction");
        //then
        Genre genre = genreRepository.get(1);
        assertEquals("fiction", genre.getName());
    }


    @Test
    public void shouldCreateAuthor() {
        //given
        addAuthor("Jan", "Nowak");

        //then
        Author author = authorRepository.get(1);
        assertEquals("Jan", author.getFirstName());
    }


    @Test
    public void shouldCreateBook() {
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");

        //when
        createBook("Akuku", "abc123", "1999-01-01", 1, 1);

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals("Akuku", bookDto.getTitle());
        assertEquals("fiction", bookDto.getGenre().getName());
        assertEquals(Arrays.asList("Nowak"), bookDto.getAuthors().stream().
                map(Author::getLastName).collect(Collectors.toList()));
        assertEquals(1, bookDto.getAuthors().size());
        assertEquals(1, bookFinder.getAll().size());
    }

    @Test
    public void shouldAddMoreBooks(){
        //given
        addGenre("fiction");
        addGenre("drama");
        addAuthor("Jan", "Nowak");
        addAuthor("Ryszard", "Sienkiewicz");

        //when
        createBook("Akuku", "abc123", "1999-01-01", 1, 2);
        createBook("Ałaa", "ddt666", "2000-01-01", 2,2);

        //then
        DetailedBookDto bookDto2 = bookFinder.getBookDetails(2);
        assertEquals(2, bookFinder.getAll().size());
        assertEquals("Akuku", bookFinder.getBookDetails(1).getTitle());
        assertEquals("Ałaa", bookFinder.getBookDetails(2).getTitle());
        assertEquals(Arrays.asList("Ryszard"),bookDto2.getAuthors().stream().
                map(Author::getFirstName).collect(Collectors.toList()));
    }


}
