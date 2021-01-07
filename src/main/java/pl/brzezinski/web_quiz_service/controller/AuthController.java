package pl.brzezinski.web_quiz_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.brzezinski.web_quiz_service.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/singup")
    public void signUp(RegisterRequest registerRequest){
    }
}
