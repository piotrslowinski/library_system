package pl.com.piotrslowinski.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.piotrslowinski.acceptance.AcceptanceTest;
import pl.com.piotrslowinski.application.BookDto;
import pl.com.piotrslowinski.application.BookFinder;
import pl.com.piotrslowinski.application.BookSearchCriteria;
import pl.com.piotrslowinski.application.BookSearchResults;
import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Genre;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookFinderTest extends AcceptanceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookFinder bookFinder;

    private BookSearchCriteria criteria = new BookSearchCriteria();

    private BookSearchResults results;


    @Autowired
    private TransactionTemplate tt;

    @Test
    public void shouldFindByTitle() {
        //given
        book().withTitle("Janko Muzykant").create();
        book().withTitle("Java").create();
        book().withTitle("Spring").create();

        //when
        criteria.setTitleQuery("ja");
        search();

        //then
        assertEquals(Arrays.asList("Janko Muzykant", "Java"),
                results.getResults().stream().
                        map(BookDto::getTitle).collect(Collectors.toList()));
    }

    @Test
    public void shouldFindByIsbn() {
        //given
        book().withTitle("Java").withIsbn("aaa").create();
        book().withTitle("Spring").withIsbn("bbb").create();

        //when
        criteria.setIsbnQuery("b");
        search();

        //then
        assertEquals(Arrays.asList("Spring"), results.getResults().stream().
                map(BookDto::getTitle).collect(Collectors.toList()));
    }

    @Test
    public void shouldFindByGenre() {
        //given
        book().withTitle("Java").withGenre("science").create();
        book().withTitle("E.T.").withGenre("fiction").create();
        book().withTitle("Spring").withGenre("science").create();

        //when
        criteria.setGenreName("fiction");
        search();

        //then
        assertEquals(Arrays.asList("E.T."), results.getResults().stream().
                map(BookDto::getTitle).collect(Collectors.toList()));
    }

    @Test
    public void shouldFindByAuthorsLastName() {
        //given
        book().withTitle("Psy").withAuthor("Jan", "Nowak").create();
        book().withTitle("Koty").withAuthor("Jan", "Nowak").create();
        book().withTitle("Ptaki").withAuthor("Adam", "Kowalski").create();

        //when
        criteria.setAuthorsLastName("nowak");
        search();

        //then
        assertEquals(Arrays.asList("Psy", "Koty"), results.getResults().stream().
                map(BookDto::getTitle).collect(Collectors.toList()));
    }

    @Test
    public void shouldFindByAuthorsLastNameAndFirstName() {
        //given
        book().withTitle("Psy").withAuthor("Jan", "Nowak").create();
        book().withTitle("Koty").withAuthor("Janina", "Nowak").create();
        book().withTitle("Ptaki").withAuthor("Adam", "Kowalski").create();

        //when
        criteria.setAuthorsLastName("Nowak");
        criteria.setAuthorsFirstName("Jan");
        search();

        //then
        assertEquals(Arrays.asList("Psy"), results.getResults().stream().
                map(BookDto::getTitle).collect(Collectors.toList()));
    }

    private void search() {
        results = bookFinder.search(criteria);
    }


    class BookBuilder {

        private String title = "Test";
        private String isbn = "abc";
        private String publishedAt = "2000-01-01";
        private Genre genre;
        private Collection<Author> authors;
        private List<Consumer<Book>> consumers = new LinkedList<>();

        BookBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        BookBuilder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        BookBuilder withDate(String date) {
            this.publishedAt = date;
            return this;
        }

        BookBuilder withGenre(String genreName) {
            consumers.add(book -> book.assignGenre(new Genre(genreName)));
            return this;
        }

        BookBuilder withAuthor(String firstName, String lastName) {
            consumers.add(book -> book.assignAuthor(new Author(firstName, lastName)));
            return this;
        }

        Book create() {
            Book book = new Book(title, isbn, LocalDate.parse(publishedAt));
            consumers.forEach(c -> c.accept(book));
            tt.execute((c) -> {
                entityManager.persist(book);
                return null;
            });
            return book;
        }
    }

    private BookBuilder book() {
        return new BookBuilder();
    }
}
