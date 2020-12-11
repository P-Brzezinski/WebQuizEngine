package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;

import java.util.List;

@Repository
public interface CompletedQuizzesRepository extends PagingAndSortingRepository<CompletedQuizz, Long> {

//    @Query("SELECT * FROM completed_quizzes WHERE userName = ?1 and isCorrect = true")
//    Page<CompletedQuizz> findAllCompetedSuccessfully(String userName, Pageable pageable);

    List<CompletedQuizz> getAllByUserNameAndSuccessfulTrue(String userName, Pageable pageable);
}
