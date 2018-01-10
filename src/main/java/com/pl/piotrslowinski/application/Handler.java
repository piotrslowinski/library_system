package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.commands.Command;

/**
 * Created by user on 07.01.2018.
 */
public interface Handler<C extends Command> {
    void handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }
}
