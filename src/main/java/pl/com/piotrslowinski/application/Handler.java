package pl.com.piotrslowinski.application;

import pl.com.piotrslowinski.model.commands.Command;


public interface Handler<C extends Command> {
    void handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }
}
