package pl.com.piotrslowinski.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;


@Entity
@Table(name = "specimens")
public class Specimen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Book book;

    private String code;

    @OneToMany(mappedBy = "specimen", cascade = CascadeType.ALL)
    private Collection<Lending> lendings = new LinkedList<>();

    public Specimen(String code) {
        this.code = code;
    }

    public Specimen(Book book, String code) {
        this.book = book;
        this.code = code;
    }

    public Specimen() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpecimensTitle(){
        return book.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specimen)) return false;
        Specimen specimen = (Specimen) o;
        return Objects.equals(getCode(), specimen.getCode());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCode());
    }
}
