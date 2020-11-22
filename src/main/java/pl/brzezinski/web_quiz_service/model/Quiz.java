package pl.brzezinski.web_quiz_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

public class Quiz {

    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @Size(min = 2)
    @NotEmpty
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    public Quiz() {
    }

    public Quiz(@NotBlank String title, @NotBlank String text, @Size(min = 2) String[] options, int[] answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        int[] x = null;
        if (Objects.equals(x, answer)) {
            int[] ints = new int[0];
            return ints;
        }
        return answer;
    }

    public void setAnswer(int[] answer) {
        Arrays.sort(answer);
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + Arrays.toString(options) +
                ", answers=" + Arrays.toString(answer) +
                '}';
    }
}
