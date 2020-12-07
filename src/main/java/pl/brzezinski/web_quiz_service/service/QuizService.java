package pl.brzezinski.web_quiz_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.brzezinski.web_quiz_service.db.QuizRepository;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.Feedback;
import pl.brzezinski.web_quiz_service.model.Quiz;

import org.springframework.data.domain.Pageable;
import pl.brzezinski.web_quiz_service.model.User;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    private UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
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

    public boolean isQuizSolved(Answer userInput, Quiz quiz) {
        List<Integer> answers = userInput.getAnswer();
        if (answers.equals(quiz.getAnswer())){
            return true;
        }
        return false;
    }

    public void deleteQuiz(long id){
        quizRepository.deleteById(id);
    }
}
