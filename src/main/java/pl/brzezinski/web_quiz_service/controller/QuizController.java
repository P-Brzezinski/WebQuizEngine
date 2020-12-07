package pl.brzezinski.web_quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.db.QuizRepository;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Feedback;
import pl.brzezinski.web_quiz_service.model.Quiz;
import pl.brzezinski.web_quiz_service.model.User;
import pl.brzezinski.web_quiz_service.service.QuizService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class QuizController {

    private QuizService quizService;
    private QuizRepository quizRepository;
    private UserRepository userRepository;

    public QuizController(QuizService quizService, QuizRepository quizRepository, UserRepository userRepository) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz newQuiz, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        newQuiz.setUser(user);
        quizRepository.save(newQuiz);
        return newQuiz;
    }

    @GetMapping(path = "quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        if (searchForQuiz(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        } else {
            return quizRepository.findById(id);
        }
    }

    @GetMapping(path = "quizzes")
    public Iterable<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        return quizService.getAllQuizzes(pageNo, pageSize, sortBy);
    }

    @PostMapping(path = "quizzes/{id}/solve")
    public Feedback solveQuiz(@RequestBody Answer userInput, @PathVariable int id) {
        Feedback feedback;
        Quiz quizToSolve = searchForQuiz(id);

        List<Integer> allAnswers = userInput.getAnswer();

        if (quizToSolve != null) {
            quizToSolve = quizRepository.findById(id);
            if (allAnswers.equals(quizToSolve.getAnswer())) {
                feedback = new Feedback(true, "Congratulations, you're right!");
            } else {
                feedback = new Feedback(false, "Wrong answer! Please, try again.");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        }
        return feedback;
    }


    @DeleteMapping(path = "quizzes/{id}")
    public void deleteQuiz(@PathVariable("id") int id, Principal principal) {
        Quiz quiz = searchForQuiz(id);

        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)");
        } else if (!Objects.equals(quiz.getUser().getEmail(), principal.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "(Forbidden)");
        } else {
            quizRepository.delete(quiz);
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "(No content)"
            );
        }
    }

    private Quiz searchForQuiz(int id) {
        return quizRepository.findById(id);
    }
}
