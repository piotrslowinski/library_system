package pl.com.piotrslowinski.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<Lending> getLendings() {
        return lendings;
    }

    public void changeProfile(String lastName, String documentNumber, String street, String city) {
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.address = new Address(street, city);
    }

    public Lending borrowBook(Specimen specimen) {
        Lending lending = null;
        if (!hasSpecimen(specimen)) {
            lending = new Lending(this, specimen);
            lendings.add(lending);
        }
        return lending;
    }

    private boolean hasSpecimen(Specimen specimen) {
        return getCurrentSpecimens().contains(specimen);
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
