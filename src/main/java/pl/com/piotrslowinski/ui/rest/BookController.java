package pl.com.piotrslowinski.ui.rest;

import pl.com.piotrslowinski.infrastructure.Secured;
import pl.com.piotrslowinski.model.repositories.BookRepository;
import org.springframework.web.bind.annotation.*;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.commands.*;

import java.util.List;


@RestController
@RequestMapping
public class BookController {

    private BookFinder bookFinder;

    private BookRepository repository;

    private CommandGateway gateway;

    private ClientFinder clientFinder;

    private AuthorFinder authorFinder;

    public BookController(BookFinder bookFinder, BookRepository repository, CommandGateway gateway,
                          ClientFinder clientFinder, AuthorFinder authorFinder) {
        this.bookFinder = bookFinder;
        this.repository = repository;
        this.gateway = gateway;
        this.clientFinder = clientFinder;
        this.authorFinder = authorFinder;
    }

    @PutMapping("/authors")
    public void addAuthor(@RequestBody AddAuthorCommand cmd){
        gateway.execute(cmd);
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAuthorsList(){
        return authorFinder.getAll();
    }

    @GetMapping("/books/{id}")
    public DetailedBookDto get(@PathVariable Integer id){
        return bookFinder.getBookDetails(id);
    }

    @Secured
    @PutMapping("/books")
    public void addNew(@RequestBody CreateBookCommand cmd){
        gateway.execute(cmd);
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks(){
        return bookFinder.getAll();
    }

    @Secured
    @PostMapping("/books/{bookId}/specimens")
    public DetailedBookDto addSpecimen(@PathVariable Integer bookId, @RequestBody AddNewSpecimenCommand cmd){
        cmd.setBookId(bookId);
        gateway.execute(cmd);
        return bookFinder.getBookDetails(bookId);
    }

    @Secured
    @DeleteMapping("books/{bookId}/specimens")
    public DetailedBookDto removeSpecimen(@PathVariable Integer bookId, @RequestBody DeleteSpecimenCommand cmd){
        cmd.setBookId(bookId);
        gateway.execute(cmd);
        return bookFinder.getBookDetails(bookId);
    }

    @Secured
    @PutMapping("books/{bookId}/authors")
    public DetailedBookDto assignToAuthor(@PathVariable Integer bookId, @RequestBody AssignAuthorCommand cmd){
        cmd.setBookId(bookId);
        gateway.execute(cmd);
        return bookFinder.getBookDetails(bookId);
    }

    @Secured
    @PutMapping("/clients/{clientId}/lendings")
    public ClientDto lendBook(@PathVariable Integer clientId, @RequestBody LendSpecimenCommand cmd){
        cmd.setClientId(clientId);
        gateway.execute(cmd);
        return clientFinder.get(clientId);
    }

    @Secured
    @DeleteMapping("/clients/{clientId}/lendings")
    public ClientDto returnBook(@PathVariable Integer clientId, @RequestBody ReturnSpecimenCommand cmd){
        cmd.setClientId(clientId);
        gateway.execute(cmd);
        return clientFinder.get(clientId);
    }


}
