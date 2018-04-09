package pl.com.piotrslowinski.acceptance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.*;
import pl.com.piotrslowinski.model.commands.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LendAndReturnBookTest extends AcceptanceTest {

    @Autowired
    private TransactionTemplate tt;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AddGenreHandler addGenreHandler;

    @Autowired
    private AddAuthorHandler addAuthorHandler;

    @Autowired
    private CreateBookHandler createBookHandler;

    @Autowired
    private RegisterClientHandler registerClientHandler;

    @Autowired
    private AddNewSpecimenHandler addNewSpecimenHandler;

    @Autowired
    private LendSpecimenHandler lendSpecimenHandler;

    @Autowired
    private ReturnSpecimenHandler returnSpecimenHandler;

    @Autowired
    private AssignAuthorHandler assignAuthorHandler;

    @Autowired
    private ClientFinder clientFinder;


    @Test
    public void shouldBorrowBook() {
        //given
        createBook("Java", "abc", "2000-01-01", 1,1);
        addNewSpecimen(1, "111");

        //when
        lendSpecimen(1, "111");

        //then
        ClientDto clientDto = clientFinder.get(1);
        assertEquals(1, clientDto.getActualLendings().size());
        assertEquals(Arrays.asList("Java"), clientDto.getActualLendings().stream().collect(Collectors.toList()));
        assertEquals(Arrays.asList("111"), clientDto.getLendingsHistory().stream().map(ClientLendingDto::getSpecimenCode).collect(Collectors.toList()));
    }

    @Test
    public void shouldBorrowManySpecimens(){
        //given
        createBook("Java", "aaa", "2000-01-01", 1,1);
        addNewSpecimen(1, "111");
        addNewSpecimen(1, "222");

        //when
        lendSpecimen(1, "111");
        lendSpecimen(1, "222");

        //then
        ClientDto clientDto = clientFinder.get(1);
        assertEquals(2, clientDto.getActualLendings().size());
        assertEquals(Arrays.asList("Java", "Java"), clientDto.getActualLendings().stream().collect(Collectors.toList()));
        assertEquals(Arrays.asList("111", "222"), clientDto.getLendingsHistory().stream().map(ClientLendingDto::getSpecimenCode).collect(Collectors.toList()));
    }

    @Test
    public void shouldBorrowManySpecimenFromDifferentBooks(){
        //given
        createBook("Java", "aaa", "2000-01-01", 1,1);
        createBook("Spring", "bbb", "2000-01-01", 1,1);
        addNewSpecimen(1, "111");
        addNewSpecimen(2, "222");

        //when
        lendSpecimen(1, "111");
        lendSpecimen(1, "222");

        //then
        ClientDto clientDto = clientFinder.get(1);
        assertEquals(2, clientDto.getActualLendings().size());
        assertEquals(Arrays.asList("Java", "Spring"), clientDto.getActualLendings().stream().collect(Collectors.toList()));
        assertEquals(Arrays.asList("111", "222"), clientDto.getLendingsHistory().stream().map(ClientLendingDto::getSpecimenCode).collect(Collectors.toList()));
    }


    @Test
    public void shouldReturnSpecimensFromDifferentBooks(){
        //given
        createBook("Java", "aaa", "2000-01-01", 1,1);
        createBook("Spring", "bbb", "2000-01-01", 1,1);
        addNewSpecimen(1, "111");
        addNewSpecimen(2, "222");
        lendSpecimen(1, "111");
        lendSpecimen(1, "222");

        //when
        returnSpecimen(1,"111");

        //then
        ClientDto clientDto = clientFinder.get(1);
        assertEquals(1, clientDto.getActualLendings().size());
        assertEquals(Arrays.asList("Spring"), clientDto.getActualLendings().stream().collect(Collectors.toList()));

    }



    @Before
    public void addGenre() {
        AddGenreCommand addGenreCommand = new AddGenreCommand();
        addGenreCommand.setName("fiction");
        addGenreHandler.handle(addGenreCommand);
    }

    @Before
    public void addAuthor() {
        AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
        addAuthorCommand.setFirstName("Adam");
        addAuthorCommand.setLastName("Nowak");
        addAuthorHandler.handle(addAuthorCommand);
    }

    private void createBook(String title, String isbn, String publishedAt, int genrId, int authorId) {
        CreateBookCommand createBookCommand = new CreateBookCommand();
        createBookCommand.setTitle(title);
        createBookCommand.setIsbn(isbn);
        createBookCommand.setPublishedAt(LocalDate.parse(publishedAt));
        createBookCommand.setGenreId(genrId);
        createBookCommand.setAuthorId(authorId);
        createBookHandler.handle(createBookCommand);
    }

    private void addNewSpecimen(Integer bookId, String code){
        AddNewSpecimenCommand cmd = new AddNewSpecimenCommand();
        cmd.setBookId(bookId);
        cmd.setCode(code);
        addNewSpecimenHandler.handle(cmd);
    }

    private void lendSpecimen(int clientId, String code) {
        LendSpecimenCommand cmd = new LendSpecimenCommand();
        cmd.setClientId(clientId);
        cmd.setCode(code);
        lendSpecimenHandler.handle(cmd);
    }

    private void returnSpecimen(int clientId, String code){
        ReturnSpecimenCommand cmd = new ReturnSpecimenCommand();
        cmd.setClientId(clientId);
        cmd.setCode(code);
        returnSpecimenHandler.handle(cmd);
    }

    @Before
    public void registerClient() {
        RegisterClientCommand cmd = new RegisterClientCommand();
        cmd.setFirstName("Jan");
        cmd.setLastName("Nowak");
        cmd.setDocumentNumber("AMD616777");
        cmd.setPesel("55112255566");
        cmd.setStreet("Lubartowska");
        cmd.setCity("Lublin");
        registerClientHandler.handle(cmd);
    }


}
