package pl.com.piotrslowinski.application.users;

import java.util.Optional;

public interface UserRepository {


    boolean isLoginOccupied(String login);

    void save(User user);

    Optional<User> get(String login);

    User get(Integer number);

    User get(String login, String password);
}


