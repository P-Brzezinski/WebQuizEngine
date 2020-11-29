package pl.brzezinski.web_quiz_service.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.brzezinski.web_quiz_service.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName (String name);
}
