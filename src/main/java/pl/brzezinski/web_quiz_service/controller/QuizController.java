package pl.brzezinski.web_quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {

    public static int NUMBER_OF_QUIZZES = 0;

    private Map<Integer, Quiz> quizList = new HashMap<>();

    public QuizController() {
//        quizList.add(new Quiz("The Java Logo", "What is depicted on the Java logo?", new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"}));
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable String id){
        if (!quizList.containsKey(Integer.parseInt(id))){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        }else {
            return quizList.get(Integer.parseInt(id));
        }
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createNewQuiz(@RequestBody Quiz newQuiz) {
        newQuiz.setId(NUMBER_OF_QUIZZES + 1);
        NUMBER_OF_QUIZZES++;
        quizList.put(newQuiz.getId(), newQuiz);
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
