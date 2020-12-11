package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.web_quiz_service.model.CompletedQuizz;

@Repository
public interface CompletedQuizzesRepository extends PagingAndSortingRepository<CompletedQuizz, Long> {

    @Query("select cq from CompletedQuizz cq where cq.userName = ?1 and cq.successful = true")
    Page<CompletedQuizz> findByUserName(String name, Pageable pageable);
}
