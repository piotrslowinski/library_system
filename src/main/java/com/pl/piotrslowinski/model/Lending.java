package com.pl.piotrslowinski.model;

import com.pl.piotrslowinski.infrastructure.StandardTimeProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by user on 07.01.2018.
 */
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

    public TimeProvider getTimeProvider() {
        return timeProvider;
    }

    @Autowired
    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
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

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBorrowed(Specimen specimen) {
        return isCurrent() && specimen.equals(this.specimen);
    }

    public boolean isCurrent() {
        return returnDate.isAfter(timeProvider.today());
    }

    public void terminate() {
        returnDate = timeProvider.today();
    }

    public String getSpecimensTitle() {
        return getSpecimen().getBook().getTitle();
    }
}
