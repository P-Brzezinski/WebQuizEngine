package pl.brzezinski.web_quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Feedback;
import pl.brzezinski.web_quiz_service.model.Quiz;

import javax.validation.Valid;
import java.util.*;

@RestController
public class QuizController {

    public static int NUMBER_OF_QUIZZES = 0;

    List<Quiz> quizList = new ArrayList<>();

    public QuizController() {
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz newQuiz) {
        NUMBER_OF_QUIZZES++;
        newQuiz.setId(NUMBER_OF_QUIZZES);
        quizList.add(newQuiz);
        return newQuiz;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        if (!isQuizExists(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        } else {
            return quizList.get(id - 1);
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

    @PostMapping(path = "/api/quizzes/{quizId}/solve")
    public Feedback solveQuiz(@RequestBody Answer answer, @PathVariable int quizId) {
        Feedback feedback;
        Quiz quizToSolve;

        int[] allAnswers = answer.getAnswer();
        Arrays.sort(allAnswers);

        if (isQuizExists(quizId)) {
            quizToSolve = quizList.get(quizId - 1);
            if (Arrays.equals(quizToSolve.getAnswer(), allAnswers)) {
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

    private boolean isQuizExists(int id) {
        id = id <= quizList.size() ? id : 0;
        return id != 0;
    }
}
