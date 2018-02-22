package com.pl.piotrslowinski.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;


@Entity
@Table(name = "specimens")
public class Specimen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   // @JoinColumn(name = "book_id")
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

    public Collection<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(Collection<Lending> lendings) {
        this.lendings = lendings;
    }

    public String getSpecimensTitle(){
        return book.getTitle();
    }
}
