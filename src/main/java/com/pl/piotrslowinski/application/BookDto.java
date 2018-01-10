package com.pl.piotrslowinski.application;

/**
 * Created by user on 07.01.2018.
 */
public class BookDto {

    private Integer id;

    private String title;

    private String isbn;



    public BookDto(Integer id, String title, String isbn ) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}