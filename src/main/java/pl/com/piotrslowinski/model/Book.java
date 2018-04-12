package pl.com.piotrslowinski.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String isbn;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private Genre genre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Author> authors = new LinkedList<>();

    @OneToMany(mappedBy = "book")
    private Collection<Specimen> specimens = new LinkedList<>();

    public Book() {
    }

    public Book(String title, String isbn, LocalDate publishedAt, Genre genre) {
        this.title = title;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
        this.genre = genre;

    }
    public Book(Integer id,String title, String isbn, LocalDate publishedA) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedAt = publishedAt;

    }

    public Book(String title, String isbn, LocalDate publishedAt) {
        this.title = title;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Specimen> getSpecimens() {
        return specimens;
    }

    public void assignGenre(Genre genre) {
        this.genre = genre;
    }

    public void assignAuthor(Author author){
        if(!isCurrentlyAssignedToBook(author))
            authors.add(author);
    }

    private boolean isCurrentlyAssignedToBook(Author author) {
        return getAuthors().contains(author);
    }

    public void unassignAuthor(Author author) {
        authors.remove(author);
    }


    public void addSpecimen(Specimen specimen) {
        if (!hasSpecimen(specimen.getCode()))
            specimens.add(specimen);
    }

    private boolean hasSpecimen(String code) {
        for(Specimen s: specimens){
            if(s.getCode().equals(code))
                return true;
        }
        return false;
    }

    public void remove(Specimen specimen) {
        specimens.remove(specimen);
    }
}
