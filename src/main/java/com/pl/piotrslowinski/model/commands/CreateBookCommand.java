package com.pl.piotrslowinski.model.commands;

import java.time.LocalDate;

/**
 * Created by user on 07.01.2018.
 */
public class CreateBookCommand implements Command {

    private String title;

    private String isbn;

    private LocalDate publishedAt;

    private Integer genreId;

    private Integer authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }



    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "title", title);
        validatePresence(errors, "isbn", isbn);

    }


    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}
