package pl.brzezinski.web_quiz_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
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

    private String owner;

    public Quiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
