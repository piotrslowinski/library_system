package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.commands.Command;
import com.pl.piotrslowinski.model.commands.CommandInvalidException;
import com.pl.piotrslowinski.model.commands.ValidationErrors;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class CommandGateway {

    private ApplicationContext applicationContext;

    public CommandGateway(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void execute(Command command){
        validate(command);
        Handler handler = handlerFor(command);
        handler.handle(command);
    }

    private void validate(Command command) {
        ValidationErrors validationErrors = new ValidationErrors();
        command.validate(validationErrors);
        if(validationErrors.any())
            throw new CommandInvalidException(validationErrors);
    }

    private Handler handlerFor(Command command){
        Map<String,Handler> handlers = applicationContext.getBeansOfType(Handler.class);
        Optional<Handler> handlerOptional = handlers.values().stream().filter((h) -> h.canHandle(command)).findFirst();
        return handlerOptional.orElseThrow( () ->
                new IllegalArgumentException("No handler found for " + command.getClass()));
    }
}
