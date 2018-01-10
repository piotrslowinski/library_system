package com.pl.piotrslowinski.model.commands;

/**
 * Created by user on 07.01.2018.
 */

public class AddAuthorCommand implements Command {


    private String firstName;

    private String lastName;

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

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "firstName", firstName);
        validatePresence(errors, "lastName", lastName);
    }
}
