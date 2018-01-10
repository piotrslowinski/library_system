package com.pl.piotrslowinski.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by user on 07.01.2018.
 */
@Entity
@Table(name = "clients")
public class Client {

    @Transient
    @Autowired
    private TimeProvider timeProvider;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "document_number")
    private String documentNumber;

    private String pesel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client")
    private Collection<Lending> lendings = new LinkedList<>();

    public Client(String firstName, String lastName, String documentNumber, String pesel, String street, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.pesel = pesel;
        this.address = new Address(street, city);
    }

    public Client(String firstName, String lastName, String documentNumber, String pesel, String street, String city, TimeProvider timeProvider) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.pesel = pesel;
        this.address = new Address(street, city);
        this.timeProvider = timeProvider;
    }

    public Client() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(Collection<Lending> lendings) {
        this.lendings = lendings;
    }

    public void changeProfile(String lastName, String documentNumber, String street, String city) {
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.address = new Address(street, city);
    }

    public void borrowBook(Lending lending) {
        if (!isCurrentlyBorrowed(lending))
            lendings.add(lending);
    }

    private boolean isCurrentlyBorrowed(Lending lending) {
        return getCurrentLendings().contains(lending);
    }

    public Collection<Lending> getCurrentLendings() {
        return lendings.stream().filter(Lending::isCurrent).collect(Collectors.toList());
    }

    public void returnBook(Specimen specimen) {
        lendings.stream().filter((lending) -> lending.isBorrowed(specimen)).findFirst().ifPresent(Lending::terminate);
    }

    public Collection<Specimen> getCurrentSpecimens() {
        return lendings.stream().filter(Lending::isCurrent).
                map(Lending::getSpecimen).collect(Collectors.toList());
    }
}
