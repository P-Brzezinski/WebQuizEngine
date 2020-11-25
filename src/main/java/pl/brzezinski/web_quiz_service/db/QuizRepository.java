package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.web_quiz_service.model.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

}
