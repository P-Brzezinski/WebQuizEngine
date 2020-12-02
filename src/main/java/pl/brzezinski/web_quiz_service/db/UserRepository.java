package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.CrudRepository;
import pl.brzezinski.web_quiz_service.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail (String email);
    boolean existsByEmail(String email);

}
