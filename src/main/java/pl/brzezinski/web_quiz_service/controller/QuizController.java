package pl.brzezinski.web_quiz_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Quiz;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizController {

    private List<Quiz> quizList = new ArrayList<>();

    public QuizController() {
        quizList.add(new Quiz("The Java Logo", "What is depicted on the Java logo?", new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"}));
    }

    @GetMapping(path = "/api/quiz")
    public Quiz getQuiz() {
        return quizList.get(0);
    }

    @PostMapping(path = "/api/quiz")
    public Answer getAnswer(@RequestParam(name = "answer") int answer){
        if (answer == 2){
            return new Answer(true, "Congratulations, you're right!");
        }else {
            return new Answer(false, "Wrong answer! Please, try again.");
        }
    }
}
