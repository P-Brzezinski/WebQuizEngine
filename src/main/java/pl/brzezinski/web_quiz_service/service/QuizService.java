package pl.brzezinski.web_quiz_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.brzezinski.web_quiz_service.db.CompletedQuizzesRepository;
import pl.brzezinski.web_quiz_service.db.QuizRepository;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;
import pl.brzezinski.web_quiz_service.model.Quiz;

import org.springframework.data.domain.Pageable;
import pl.brzezinski.web_quiz_service.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private CompletedQuizzesRepository completedQuizzesRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, CompletedQuizzesRepository completedQuizzesRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completedQuizzesRepository = completedQuizzesRepository;
    }

    public Quiz saveNewQuiz(Quiz quiz, String userName) {
        User user = userRepository.findByEmail(userName);
        quiz.setUser(user);
        quizRepository.save(quiz);
        return quiz;
    }

    public Quiz getQuizById(long id) {
        return quizRepository.findById(id);
    }

    public Iterable<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    public Iterable<CompletedQuizz> getAllCompletedQuizzes(String userName, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        return completedQuizzesRepository.getAllByUserNameAndSuccessfulTrue(userName, paging);
    }

    public boolean isQuizSolved(Answer userInput, Quiz quiz) {
        List<Integer> answers = userInput.getAnswer();
        if (answers.equals(quiz.getAnswer())) {
            return true;
        }
        return false;
    }

    public void saveCompletedQuiz(long quizId, String userName, boolean isCorrect) {
        CompletedQuizz completedQuizz = new CompletedQuizz();
        completedQuizz.setQuizId(quizId);
        completedQuizz.setUserName(userName);
        completedQuizz.setSuccessful(isCorrect);
        completedQuizz.setCompletedAt(LocalDateTime.now());
        completedQuizzesRepository.save(completedQuizz);
    }

    public void deleteQuiz(long id) {
        quizRepository.deleteById(id);
    }
}
