package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Address;
import com.pl.piotrslowinski.model.Client;
import com.pl.piotrslowinski.model.Specimen;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 07.01.2018.
 */
public class ClientDto {

    private String firstName;

    private String lastName;

    private String documentNumber;

    private String pesel;

    private Address address;

    private List<ClientLendingDto> lendingsHistory;

    private List<String> actualLendings;

    public ClientDto(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.documentNumber = client.getDocumentNumber();
        this.pesel = client.getPesel();
        this.address = client.getAddress();
        this.lendingsHistory = client.getLendings().stream().map(ClientLendingDto::new).collect(Collectors.toList());
        this.actualLendings = client.getCurrentSpecimens().stream().
                map(Specimen::getSpecimensTitle).
                collect(Collectors.toList());
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

    public List<ClientLendingDto> getLendingsHistory() {
        return lendingsHistory;
    }

    public void setLendingsHistory(List<ClientLendingDto> lendingsHistory) {
        this.lendingsHistory = lendingsHistory;
    }

    public List<String> getActualLendings() {
        return actualLendings;
    }

    public void setActualLendings(List<String> actualLendings) {
        this.actualLendings = actualLendings;
    }
}
