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
import java.util.*;

@RestController
public class QuizController {

    public static int NUMBER_OF_QUIZZES = 0;

    List<Quiz> quizList = new ArrayList<>();
    private QuizRepository quizRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz newQuiz) {
        NUMBER_OF_QUIZZES++;
//        newQuiz.setId(NUMBER_OF_QUIZZES);
//        quizList.add(newQuiz);
        quizRepository.save(newQuiz);
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
        List<Quiz> quizzesFromDB = (List<Quiz>) quizRepository.findAll();
        if (quizzesFromDB.isEmpty()){
            return new Quiz[0];
        }else {
            Quiz[] quizzes = new Quiz[quizzesFromDB.size()];
            for (int i = 0; i < quizzes.length; i++) {
                quizzes[i] = quizzesFromDB.get(i);
            }
            return quizzes;
        }
    }

    @PostMapping(path = "/api/quizzes/{quizId}/solve")
    public Feedback solveQuiz(@RequestBody Answer answer, @PathVariable int quizId) {
        Feedback feedback;
        Quiz quizToSolve;

        List<Integer> allAnswers = answer.getAnswer();

        if (isQuizExists(quizId)) {
            quizToSolve = quizList.get(quizId - 1);
            if (quizToSolve.getAnswer().equals(allAnswers)) {
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
