package pl.com.piotrslowinski.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "lendings")
public class Lending {

    @Transient
    @Autowired
    private TimeProvider timeProvider;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Specimen specimen;

    @Column(name = "lending_date")
    private LocalDate lendingDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Lending(Client client, Specimen specimen, TimeProvider timeProvider) {
        this.client = client;
        this.specimen = specimen;
        this.timeProvider = timeProvider;
        this.lendingDate = timeProvider.today();
        returnDate = TimeProvider.MAX_DATE;
    }

    public Lending() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Specimen getSpecimen() {
        return specimen;
    }

    public void setSpecimen(Specimen specimen) {
        this.specimen = specimen;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isCurrent() {
        return returnDate.isAfter(LocalDate.now());
    }

    public void terminate() {
        returnDate = LocalDate.now();
    }

}
