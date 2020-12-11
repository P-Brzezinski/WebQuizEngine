package pl.brzezinski.web_quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.model.User;
import pl.brzezinski.web_quiz_service.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register", consumes = "application/json")
    public void registerNewUser(@Valid @RequestBody User user) {
        if (userService.isExistsByEmail(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        } else {
            userService.registerNewUser(user);
        }
    }

    @GetMapping(path = "users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
