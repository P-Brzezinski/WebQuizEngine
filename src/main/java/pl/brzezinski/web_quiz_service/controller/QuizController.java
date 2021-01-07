package pl.brzezinski.web_quiz_service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.brzezinski.web_quiz_service.dto.QuizDto;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;
import pl.brzezinski.web_quiz_service.model.Feedback;
import pl.brzezinski.web_quiz_service.model.Quiz;
import pl.brzezinski.web_quiz_service.service.QuizService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
@AllArgsConstructor
@Slf4j
public class QuizController {

    private QuizService quizService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<QuizDto> createNewQuiz(@Valid @RequestBody QuizDto quizDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.save(quizDto));
    }

    @GetMapping(path = "{id}")
    public Quiz getQuizById(@PathVariable long id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        }
        return quiz;
    }

    @GetMapping()
    public ResponseEntity<List<QuizDto>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.status(HttpStatus.OK).body(quizService.getAllQuizzes(pageNo, pageSize, sortBy));
    }

    @GetMapping(path = "quizzes/completed")
    public Page<CompletedQuizz> getAllCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy,
            Principal principal) {
        return quizService.getAllCompletedQuizzes(principal.getName(), pageNo, pageSize, sortBy);
    }

    @PostMapping(path = "quizzes/{id}/solve")
    public Feedback solveQuiz(@RequestBody Answer userInput, @PathVariable long id, Principal principal) {
        Feedback feedback;
        Quiz quiz = quizService.getQuizById(id);

        if (quiz != null) {
            boolean isCorrect;
            if (quizService.isQuizSolved(userInput, quiz)) {
                feedback = new Feedback(true, "Congratulations, you're right!");
                isCorrect = true;
            } else {
                feedback = new Feedback(false, "Wrong answer! Please, try again.");
                isCorrect = false;
            }
            quizService.saveCompletedQuiz(quiz.getId(), principal.getName(), isCorrect);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)"
            );
        }
        return feedback;
    }


    @DeleteMapping(path = "quizzes/{id}")
    public void deleteQuiz(@PathVariable("id") long id, Principal principal) {
        Quiz quiz = quizService.getQuizById(id);

        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "(Not found)");
        } else if (!Objects.equals(quiz.getUser().getEmail(), principal.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "(Forbidden)");
        } else {
            quizService.deleteQuiz(id);
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "(No content)"
            );
        }
    }
}
