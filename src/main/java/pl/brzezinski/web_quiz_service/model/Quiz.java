package pl.brzezinski.web_quiz_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Text is required")
    private String text;

    @ElementCollection
    @JoinColumn(name = "quiz_id")
    @Size(min = 2)
    @NotEmpty
    private List<String> options;

    @ElementCollection
    @JoinColumn(name = "quiz_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public List<Integer> getAnswer() {
        List<Integer> x = null;
        if (Objects.equals(x, answer)) {
            List<Integer> ints = new ArrayList<>();
            return ints;
        }
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        Collections.sort(answer);
        this.answer = answer;
    }
}
