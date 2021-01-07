package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.CrudRepository;
import pl.brzezinski.web_quiz_service.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail (String email);
    boolean existsByEmail(String email);

    Optional<User> findByUserName(String userName);
}
