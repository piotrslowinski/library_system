package pl.com.piotrslowinski.acceptance;

import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.commands.*;
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
    private AssignAuthorHandler assignAuthorHandler;

    @Autowired
    private BookFinder bookFinder;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;


    public void addGenre(String genreName) {
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName(genreName);
        addGenreHandler.handle(addGenreCommand);
    }

    private void addAuthor(String firstName, String lastName) {
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName(firstName);
        addAuthorCommand.setLastName(lastName);
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

    public void assignAuthor(int bookId, int authorId) {
        AssignAuthorCommand cmd = new AssignAuthorCommand();
        cmd.setBookId(bookId);
        cmd.setAuthorId(authorId);
        assignAuthorHandler.handle(cmd);

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
    public void shouldAddMoreGenres() {
        //given
        addGenre("fiction");
        addGenre("drama");

        //then
        assertEquals(Integer.valueOf(1), genreRepository.get(1).getId());
        assertEquals(Integer.valueOf(2), genreRepository.get(2).getId());
        assertEquals("fiction", genreRepository.get(1).getName());
        assertEquals("drama", genreRepository.get(2).getName());
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
    public void shouldCreateMoreAuthors() {
        //given
        addAuthor("Jan", "Nowak");
        addAuthor("Adam", "Kowalski");

        //then
        AuthorDto authorDto1 = new AuthorDto(authorRepository.get(1));
        AuthorDto authorDto2 = new AuthorDto(authorRepository.get(2));
        assertEquals(Integer.valueOf(1), authorDto1.getId());
        assertEquals(Integer.valueOf(2), authorDto2.getId());
        assertEquals("Jan", authorDto1.getFirstName());
        assertEquals("Adam", authorDto2.getFirstName());
        assertEquals("Kowalski", authorDto2.getLastName());
        assertEquals("Nowak", authorDto1.getLastName());
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
    public void shouldAddMoreBooks() {
        //given
        addGenre("fiction");
        addGenre("drama");
        addAuthor("Jan", "Nowak");
        addAuthor("Ryszard", "Sienkiewicz");

        //when
        createBook("Akuku", "abc123", "1999-01-01", 1, 1);
        createBook("Ałaa", "ddt666", "2000-01-01", 2, 2);

        //then
        DetailedBookDto bookDto1 = bookFinder.getBookDetails(1);
        DetailedBookDto bookDto2 = bookFinder.getBookDetails(2);
        assertEquals(2, bookFinder.getAll().size());
        assertEquals("Akuku", bookFinder.getBookDetails(1).getTitle());
        assertEquals("Ałaa", bookFinder.getBookDetails(2).getTitle());
        assertEquals(Arrays.asList("Jan"), bookDto1.getAuthors().stream().
                map(Author::getFirstName).collect(Collectors.toList()));
        assertEquals(Arrays.asList("Ryszard"), bookDto2.getAuthors().stream().
                map(Author::getFirstName).collect(Collectors.toList()));
        assertEquals("fiction", bookDto1.getGenre().getName());
        assertEquals("drama", bookDto2.getGenre().getName());
    }

    @Test
    public void shouldAssignAuthorsWhileCreatingBook() {
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        addAuthor("John", "Doe");

        //when
        createBook("Java", "abc123", "2000-01-01", 1, 2);

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals("John", bookDto.getAuthors().stream().map(Author::getFirstName).findFirst().get());
        assertEquals("Doe", bookDto.getAuthors().stream().map(Author::getLastName).findFirst().get());
    }

    @Test
    public void shouldAssignManyAuthorToExistingBook() {
        //given
        addGenre("fiction");
        addAuthor("Jan", "Nowak");
        addAuthor("Adam", "Kowalski");
        addAuthor("Janina", "Kowalska");
        createBook("Test", "abc", "2000-01-01", 1, 1);

        //when
        assignAuthor(1, 2);
        assignAuthor(1, 3);

        //then
        DetailedBookDto bookDto = bookFinder.getBookDetails(1);
        assertEquals(3, bookDto.getAuthors().size());
        assertEquals(Arrays.asList("Nowak", "Kowalski", "Kowalska"), bookDto.getAuthors().stream().
                map(Author::getLastName).collect(Collectors.toList()));
    }


}
