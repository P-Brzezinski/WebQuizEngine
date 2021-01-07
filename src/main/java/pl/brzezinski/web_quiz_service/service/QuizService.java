package pl.brzezinski.web_quiz_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.brzezinski.web_quiz_service.db.CompletedQuizzesRepository;
import pl.brzezinski.web_quiz_service.db.QuizRepository;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.dto.QuizDto;
import pl.brzezinski.web_quiz_service.model.Answer;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;
import pl.brzezinski.web_quiz_service.model.Quiz;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class QuizService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private CompletedQuizzesRepository completedQuizzesRepository;

    @Transactional
    public QuizDto save(QuizDto quizDto) {
        Quiz quiz = quizRepository.save(mapQuizDto(quizDto));
        quizDto.setId(quiz.getId());
        return quizDto;
    }

    private Quiz mapQuizDto(QuizDto quizDto) {
        return Quiz.builder()
                .title(quizDto.getTitle())
                .text(quizDto.getText())
                .options(quizDto.getOptions())
                .answer(quizDto.getAnswer())
                .build();
    }

    public Quiz getQuizById(long id) {
        return quizRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<QuizDto> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private QuizDto mapToDto(Quiz quiz) {
        return QuizDto.builder()
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(quiz.getOptions())
                .answer(quiz.getAnswer())
                .build();
    }

    public Page<CompletedQuizz> getAllCompletedQuizzes(String userName, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        return completedQuizzesRepository.findByUserName(userName, paging);
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
