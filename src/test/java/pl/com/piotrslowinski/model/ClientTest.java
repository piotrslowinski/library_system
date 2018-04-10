package pl.com.piotrslowinski.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class ClientTest {

    @Autowired
    private final TimeMachine timeMachine = new TimeMachine();

    public Client client = new Client("Jan", "Nowak", "AMG416777",
            "910906718", "Lwowska", "Lublin", timeMachine);
    public Book book = new Book("Test", "abc", LocalDate.parse("1900-01-01"));

    @Test
    public void shouldChangeClientProfile() {
        client.changeProfile("Kowalski", "ABC997", "Warszawska", "Chełm");

        //then
        assertEquals("Kowalski", client.getLastName());
        assertEquals("ABC997", client.getDocumentNumber());
        assertEquals("Chełm", client.getAddress().getCity());
    }

    @Test
    public void shouldNotBorrowSameSpecimenTwice() {
        //when
        Specimen s1 = new Specimen(book, "abc1");
        book.addSpecimen(s1);

        //when
        client.borrowBook(s1);
        client.borrowBook(s1);


        //then
        Collection<Specimen> specimens = client.getCurrentSpecimens();
        assertEquals(Arrays.asList(s1), specimens);
        assertEquals(1, specimens.size());
    }

    @Test
    public void shouldBorrowSomeSpecimens() {
        //given
        Specimen s1 = new Specimen(book, "a");
        Specimen s2 = new Specimen(book, "b");
        Specimen s3 = new Specimen(book, "c");
        book.addSpecimen(s1);
        book.addSpecimen(s2);
        book.addSpecimen(s3);

        //when
        client.borrowBook(s1);
        client.borrowBook(s2);
        client.borrowBook(s3);
        client.borrowBook(s3);

        //then
        assertEquals(3, client.getCurrentLendings().size());
        assertEquals(Arrays.asList("a", "b", "c"), client.getCurrentSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList()));

    }


    @Test
    public void shouldNotReturnTheSameSpecimenTwice() {
        //given
        Specimen s1 = new Specimen(book, "a");
        Specimen s2 = new Specimen(book, "b");
        book.addSpecimen(s1);
        book.addSpecimen(s2);

        client.borrowBook(s1);
        client.borrowBook(s2);

        //when
        client.returnBook(s1);
        client.returnBook(s1);

        //then
        assertEquals(1, client.getCurrentSpecimens().size());
        assertEquals(Arrays.asList("b"), client.getCurrentSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnBooks() {
        //given
        Specimen s1 = new Specimen(book, "a");
        Specimen s2 = new Specimen(book, "b");
        Specimen s3 = new Specimen(book, "c");
        book.addSpecimen(s1);
        book.addSpecimen(s2);
        book.addSpecimen(s3);

        client.borrowBook(s1);
        client.borrowBook(s1);
        client.borrowBook(s2);
        client.borrowBook(s3);


        //when
        client.returnBook(s1);
        client.returnBook(s3);
        client.returnBook(s3);

        //then
        assertEquals(1, client.getCurrentLendings().size());
        assertEquals(Arrays.asList("b"), client.getCurrentSpecimens().stream().map(Specimen::getCode).collect(Collectors.toList()));


    }

//    @Test
//    public void shouldKeepLendingsHistory() {
//        //given
//        Specimen s1 = new Specimen("a");
//        Specimen s2 = new Specimen("b");
//        Specimen s3 = new Specimen("c");
//        book.addSpecimen(s1);
//        book.addSpecimen(s2);
//        book.addSpecimen(s3);
//
//
//        //when
//        timeMachine.travel(Duration.ofDays(-20));
//        LocalDate t0 = timeMachine.today();
//        client.borrowBook(s1);
//        timeMachine.travel(Duration.ofDays(10));
//        LocalDate t1 = timeMachine.today();
//        client.borrowBook(s2);
//        timeMachine.travel(Duration.ofDays(5));
//        LocalDate t2 = timeMachine.today();
//        client.borrowBook(s3);
//
//        //then
//        Collection<Lending> history = client.getLendings();
//        assertEquals(3, history.size());
//        assertEquals(Arrays.asList(s1, s2, s3), history.stream().
//                map(Lending::getSpecimen).collect(Collectors.toList()));
//        assertEquals(Arrays.asList(t0, t1, t2), history.stream().map(Lending::getLendingDate).collect(Collectors.toList()));
//        assertEquals(Arrays.asList(TimeProvider.MAX_DATE, TimeProvider.MAX_DATE, TimeProvider.MAX_DATE), history.stream()
//                .map(Lending::getReturnDate).collect(Collectors.toList()));
//    }

}
