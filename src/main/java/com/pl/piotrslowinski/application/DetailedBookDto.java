package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Author;
import com.pl.piotrslowinski.model.Book;
import com.pl.piotrslowinski.model.Genre;
import com.pl.piotrslowinski.model.Specimen;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class DetailedBookDto extends BookDto {


    private List<Author> authors;

    private LocalDate publishedAt;

    private Genre genre;

    private List<String> specimensCode;

    public DetailedBookDto(Book book) {
        super(book.getId(), book.getTitle(), book.getIsbn());
        this.authors = book.getAuthors().stream().collect(Collectors.toList());
        this.publishedAt = book.getPublishedAt();
        this.genre = book.getGenre();
        this.specimensCode = book.getSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList());
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<String> getSpecimensCode() {
        return specimensCode;
    }

    public void setSpecimensCode(List<String> specimensCode) {
        this.specimensCode = specimensCode;
    }
}
