package pl.com.piotrslowinski.model;

import pl.com.piotrslowinski.model.Author;
import pl.com.piotrslowinski.model.Book;
import pl.com.piotrslowinski.model.Genre;
import pl.com.piotrslowinski.model.Specimen;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class BookTest {

    private Genre g1 = new Genre("poetry");
    private Genre g2 = new Genre("fiction");
    private Book b1 = new Book(1, "Testowa", "abc", LocalDate.parse("1999-01-01"));
    private Author author1 = new Author(1, "Adam", "Mickiewicz");
    private Author author2 = new Author(2, "Adamus", "Mickiewiczus");

    @Test
    public void shouldAssignAuthor() {
        //given
        b1.assignAuthor(author1);
        //then
        assertEquals(Arrays.asList(author1), b1.getAuthors());
    }

    @Test
    public void shouldAssignManyAuthors() {
        //when
        b1.assignAuthor(author1);
        b1.assignAuthor(author2);

        //then
        assertEquals(Arrays.asList(author1, author2), b1.getAuthors());
    }

    //
    @Test
    public void shouldNotAssignTwiceTheSameAuthor() {
        //when
        b1.assignAuthor(author1);
        b1.assignAuthor(author1);

        //then
        Collection<Author> authors = b1.getAuthors();
        assertEquals(Arrays.asList(author1), b1.getAuthors());
        assertEquals(1, authors.size());
    }

    //
    @Test
    public void shouldDeleteWrongAuthor() {
        //when
        b1.assignAuthor(author1);
        b1.assignAuthor(author2);

        b1.unassignAuthor(author2);

        //then
        Collection<Author> authors = b1.getAuthors();
        assertEquals(Arrays.asList(author1), authors);
        assertEquals(1, authors.size());
    }

    @Test
    public void shouldAssignGenre() {
        //when
        b1.assignGenre(g1);
        b1.assignGenre(g2);
        //then
        assertEquals(g2, b1.getGenre());
    }

    @Test
    public void shouldAddSpecimen() {
        //when
        b1.addSpecimen(new Specimen("1"));
        b1.addSpecimen(new Specimen("1"));
        b1.addSpecimen(new Specimen("2"));

        //then
        assertEquals(Arrays.asList("1", "2"), b1.getSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList()));
        assertEquals(2, b1.getSpecimens().size());
    }

    @Test
    public void shouldRemoveSpecimen() {
        //given
        Specimen s1 = new Specimen("1");
        b1.addSpecimen(s1);
        b1.addSpecimen(s1);
        b1.addSpecimen(new Specimen("2"));

        //when
        b1.remove(s1);

        //then
        assertEquals(Arrays.asList("2"), b1.getSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList()));
        assertEquals(1, b1.getSpecimens().size());
    }

}
