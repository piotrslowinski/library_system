package pl.com.piotrslowinski.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.application.users.RegisterUserCommand;
import pl.com.piotrslowinski.application.users.User;
import pl.com.piotrslowinski.application.users.UserRepository;
import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.CommandInvalidException;
import pl.com.piotrslowinski.model.commands.ValidationErrors;

@Component
@Transactional
public class RegisterUserHandler implements Handler<RegisterUserCommand> {

    private UserRepository userRepository;

    public RegisterUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(RegisterUserCommand command) {
        validateLoginFree(command);
        userRepository.save(new User(command.getLogin(), command.getPassword()));
    }

    private void validateLoginFree(RegisterUserCommand cmd) {
        if (userRepository.isLoginOccupied(cmd.getLogin())) {
            ValidationErrors errors = new ValidationErrors();
            errors.add("login", "is occupied");
            throw new CommandInvalidException(errors);
        }
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterUserCommand.class;
    }
}
