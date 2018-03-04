package pl.com.piotrslowinski.application;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BookFinder {


    List<BookDto> getAll();

    DetailedBookDto getBookDetails(Integer bookId);
}
