package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.commands.Command;


public interface Handler<C extends Command> {
    void handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }
}
