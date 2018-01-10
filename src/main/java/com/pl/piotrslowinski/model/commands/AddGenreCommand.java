package com.pl.piotrslowinski.model.commands;

import org.springframework.stereotype.Component;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class AddGenreCommand implements Command {


    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors, "name", name);
    }
}
