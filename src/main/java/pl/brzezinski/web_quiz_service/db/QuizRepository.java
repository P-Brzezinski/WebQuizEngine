package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.web_quiz_service.model.Quiz;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

    Quiz findById(long id);

}
