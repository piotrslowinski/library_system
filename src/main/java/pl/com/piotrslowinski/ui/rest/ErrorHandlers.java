package pl.com.piotrslowinski.ui.rest;

import pl.com.piotrslowinski.infrastructure.NoSuchEntityException;
import pl.com.piotrslowinski.model.commands.CommandInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorHandlers {

    @ResponseStatus(code = HttpStatus.NOT_FOUND,
            reason = "Entity with given id does not exist")
    @ExceptionHandler(NoSuchEntityException.class)
    public void handleEntityNotFound() {

    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED,
            reason = "No required authentication")
    @ExceptionHandler(SecurityException.class)
    public void handleSecurityException() {

    }

    @ExceptionHandler(CommandInvalidException.class)
    public ResponseEntity handleInvalidCommand(CommandInvalidException ex) {
        return new ResponseEntity(ex.getValidationErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
