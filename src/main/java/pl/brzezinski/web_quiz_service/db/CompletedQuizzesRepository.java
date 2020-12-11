package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletedQuizzesRepository extends PagingAndSortingRepository<CompletedQuizzesRepository, Long> {
}
