package pl.brzezinski.web_quiz_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.db.QuizRepository;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Feedback;
import pl.brzezinski.web_quiz_service.model.Quiz;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
public class QuizController {

    private QuizRepository quizRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz newQuiz, Principal principal) {
        String name = principal.getName();
        newQuiz.setOwner(name);
        quizRepository.save(newQuiz);
        return newQuiz;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        if (searchForQuiz(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        } else {
            return quizRepository.findById(id);
        }
    }

    @GetMapping(path = "/api/quizzes")
    public Quiz[] getAllQuizzes() {
        List<Quiz> quizzesFromDB = (List<Quiz>) quizRepository.findAll();
        if (quizzesFromDB.isEmpty()) {
            return new Quiz[0];
        } else {
            Quiz[] quizzes = new Quiz[quizzesFromDB.size()];
            for (int i = 0; i < quizzes.length; i++) {
                quizzes[i] = quizzesFromDB.get(i);
            }
            return quizzes;
        }
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
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


    @DeleteMapping(path = "/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable("id") int id, Principal principal) {
        Quiz quiz = searchForQuiz(id);

        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)");
        } else if (!Objects.equals(quiz.getOwner(), principal.getName())) {
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
