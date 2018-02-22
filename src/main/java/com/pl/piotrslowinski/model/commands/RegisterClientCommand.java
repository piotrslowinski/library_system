package com.pl.piotrslowinski.model.commands;


public class RegisterClientCommand implements Command {

    private String firstName, lastName, documentNumber, pesel;

    private String street, city;

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


    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "firstName", firstName);
        validatePresence(errors, "lastName", lastName);
        validatePresence(errors, "documentNumber", documentNumber);
        validatePresence(errors, "pesel", pesel);
        validatePresence(errors, "street", street);
        validatePresence(errors, "city", city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
