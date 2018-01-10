package com.pl.piotrslowinski.application;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 07.01.2018.
 */
@Component
public interface BookFinder {


    List<BookDto> getAll();

    DetailedBookDto getBookDetails(Integer bookId);
}
