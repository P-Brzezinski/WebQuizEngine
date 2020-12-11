package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;

public interface CompletedQuizzesRepository extends PagingAndSortingRepository<CompletedQuizz, Long> {
}
