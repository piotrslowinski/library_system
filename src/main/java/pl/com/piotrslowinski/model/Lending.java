package pl.com.piotrslowinski.model;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.piotrslowinski.infrastructure.StandardTimeProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


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

    public Lending(Client client, Specimen specimen) {
        this.client = client;
        this.specimen = specimen;
        this.lendingDate = LocalDate.now();
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

    public boolean isBorrowed(Specimen specimen) {
        return isCurrent() && specimen.equals(this.specimen);
    }

    public void terminate() {
        returnDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lending)) return false;
        Lending lending = (Lending) o;
        return Objects.equals(getSpecimen(), lending.getSpecimen());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSpecimen());
    }
}
