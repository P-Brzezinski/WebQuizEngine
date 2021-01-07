package pl.brzezinski.web_quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User name is required")
    private String userName;

    @Email(regexp = ".+\\..+")
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "At least 5 characters are required")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    private Instant created;

    private boolean enabled;
}
