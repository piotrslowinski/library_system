package pl.com.piotrslowinski.application.users;

import pl.com.piotrslowinski.model.commands.Command;
import pl.com.piotrslowinski.model.commands.ValidationErrors;

public class RegisterUserCommand implements Command {

    private String login;

    private String password;

    private String repeatedPassword;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "login", login);
        validatePresence(errors, "password", password);
        validatePresence(errors, "repeatedPassword", repeatedPassword);
        validateFormat(errors, "login", login, "^[\\w\\d]+$");
        validateMinLength(errors, "password", password, 6);
        validatePasswordsMath(password, repeatedPassword, errors);
    }

    private void validatePasswordsMath(String password, String repeatedPassword, ValidationErrors errors) {
        if(password != null && repeatedPassword!= null && password.equals(repeatedPassword)){
            errors.add("password", "password mismatch");
            errors.add("repeatedPassword", "password mismatch");
        }
    }


}
