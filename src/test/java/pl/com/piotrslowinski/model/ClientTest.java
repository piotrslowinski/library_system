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
            "910906718", "Lwowska","Lublin", timeMachine);
    public Book book = new Book("Test", "abc", LocalDate.parse("1900-01-01"));

    @Test
    public void shouldChangeClientProfile(){
        client.changeProfile("Kowalski", "ABC997", "Warszawska", "Chełm");

        //then
        assertEquals("Kowalski", client.getLastName());
        assertEquals("ABC997", client.getDocumentNumber());
        assertEquals("Chełm", client.getAddress().getCity());
    }

    @Test
    public void shouldBorrowBooks(){
        //when
        Specimen s1 = new Specimen("abc1");
        book.addSpecimen(s1);

        //when
        Lending l = new Lending(client, s1, timeMachine);
        client.borrowBook(l);
        client.borrowBook(l);


        //then
        Collection<Lending> lendings = client.getCurrentLendings();
        assertEquals(Arrays.asList(l), client.getCurrentLendings());
        assertEquals(1, client.getCurrentLendings().size());
    }

    @Test
    public void shouldReturnBooks(){
        //given
        Specimen s1 = new Specimen("a");
        Specimen s2 = new Specimen("b");
        book.addSpecimen(s1);
        book.addSpecimen(s2);

        Lending l = new Lending(client, s1, timeMachine);
        Lending l2 = new Lending(client, s2, timeMachine);
        client.borrowBook(l);
        client.borrowBook(l2);

        //when
        client.returnBook(l);
        client.returnBook(l);


        //then
        assertEquals(1, client.getCurrentLendings().size());

    }

    @Test
    public void shouldKeepLendingsHistory(){
        //given
        Specimen s1 = new Specimen("a");
        Specimen s2 = new Specimen("b");
        Specimen s3 = new Specimen("c");
        book.addSpecimen(s1);
        book.addSpecimen(s2);
        book.addSpecimen(s3);


        //when
        timeMachine.travel(Duration.ofDays(-20));
        LocalDate t0 = timeMachine.today();
        Lending l = new Lending(client, s1, timeMachine);
        client.borrowBook(l);
        timeMachine.travel(Duration.ofDays(10));
        LocalDate t1 = timeMachine.today();
        Lending l2 = new Lending(client, s2, timeMachine);
        client.borrowBook(l2);
        timeMachine.travel(Duration.ofDays(5));
        LocalDate t2 = timeMachine.today();
        Lending l3 = new Lending(client, s3, timeMachine);
        client.borrowBook(l3);

        //then
        Collection<Lending> history = client.getLendings();
        assertEquals(3, history.size());
        assertEquals(Arrays.asList(s1,s2,s3),history.stream().
                map((lending) -> lending.getSpecimen()).collect(Collectors.toList()));
        assertEquals(Arrays.asList(t0,t1,t2), history.stream().map(Lending::getLendingDate).collect(Collectors.toList()));
        assertEquals(Arrays.asList(TimeProvider.MAX_DATE,TimeProvider.MAX_DATE,TimeProvider.MAX_DATE), history.stream()
                .map(Lending::getReturnDate).collect(Collectors.toList()));
    }

}
