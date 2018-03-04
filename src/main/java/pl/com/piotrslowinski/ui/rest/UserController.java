package pl.com.piotrslowinski.ui.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.piotrslowinski.application.CommandGateway;
import pl.com.piotrslowinski.application.users.*;

import java.util.Currency;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private CommandGateway commandGateway;
    private CurrentUser currentUser;

    public UserController(CommandGateway commandGateway, CurrentUser currentUser) {
        this.commandGateway = commandGateway;
        this.currentUser = currentUser;
    }

    @PostMapping
    public void register(@RequestBody RegisterUserCommand cmd) {
        commandGateway.execute(cmd);
    }

    @PutMapping("/{id}")
    public void updateProfile(@PathVariable Integer id, @RequestBody UpdateUserProfileCommand cmd) {
        cmd.setUserId(id);
        commandGateway.execute(cmd);
    }

    @PutMapping("/session")
    public void login(@RequestBody LoginCommand cmd) {
        commandGateway.execute(cmd);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrent() {
        Optional<UserDto> userDtoOptional = currentUser.getUserInfo();
        if (userDtoOptional.isPresent())
            return new ResponseEntity<>(userDtoOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/session")
    public void logout() {
        currentUser.logout();
    }
}
