package pl.brzezinski.web_quiz_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.brzezinski.web_quiz_service.db.UserRepository;
import pl.brzezinski.web_quiz_service.model.User;

import java.util.Arrays;

public class MyDatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byName = userRepository.findByName(username);
        if (byName == null) {
            throw new UsernameNotFoundException("Not found");
        } else {
            return new org.springframework.security.core.userdetails.User(byName.getName(), byName.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        }
    }
}
