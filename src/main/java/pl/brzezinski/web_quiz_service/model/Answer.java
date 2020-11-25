package pl.brzezinski.web_quiz_service.model;

import java.util.*;

public class Answer {

    private List<Integer> answer;

    public Answer() {
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
}
