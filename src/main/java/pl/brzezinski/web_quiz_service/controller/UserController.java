package pl.brzezinski.web_quiz_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/api/register", consumes = "application/json")
    public void registerNewUser(@Valid @RequestBody User user) {
        if (userRepository.findByName(user.getName()) == null){ //user does not exists yet
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }else { // username exists
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
    }

    @GetMapping(path = "/api/users")
    public User[] getAllUsers() {
        List<User> all = (List<User>) userRepository.findAll();
        if (all.isEmpty()) {
            return new User[0];
        } else {
            User[] allUsers = new User[all.size()];
            for (int i = 0; i < allUsers.length; i++) {
                allUsers[i] = all.get(i);
            }
            return allUsers;
        }
    }
}
