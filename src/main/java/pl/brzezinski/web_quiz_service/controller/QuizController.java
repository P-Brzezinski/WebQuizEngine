package pl.brzezinski.web_quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Quiz;

import java.lang.reflect.Array;
import java.util.*;

@RestController
public class QuizController {

    public static int NUMBER_OF_QUIZZES = 0;

    List<Quiz> quizList = new ArrayList<>();

    public QuizController() {
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable String id) {
        Quiz quiz = quizList.get(Integer.parseInt(id));
        if (quiz == null || quizList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        } else {
            return quizList.get(Integer.parseInt(id + 1));
        }
    }

    @GetMapping(path = "/api/quizzes")
    public Quiz[] getAllQuizzes() {
        if (quizList.isEmpty()) {
            return new Quiz[0];
        } else {
            Quiz[] quizzes = new Quiz[quizList.size()];
            for (int i = 0; i < quizzes.length; i++) {
                quizzes[i] = quizList.get(i);
            }
            return quizzes;
        }
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@RequestBody Quiz newQuiz) {
        newQuiz.setId(NUMBER_OF_QUIZZES + 1);
        NUMBER_OF_QUIZZES++;
        quizList.add(newQuiz);
        return newQuiz;
    }

    @PostMapping(path = "/api/quiz")
    public Answer getAnswer(@RequestParam(name = "answer") int answer) {
        if (answer == 2) {
            return new Answer(true, "Congratulations, you're right!");
        } else {
            return new Answer(false, "Wrong answer! Please, try again.");
        }
    }
}
