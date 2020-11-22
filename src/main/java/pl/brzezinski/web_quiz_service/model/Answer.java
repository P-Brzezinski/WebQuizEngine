package pl.brzezinski.web_quiz_service.model;

import java.util.Arrays;
import java.util.Objects;

public class Answer {

    private int[] answer;

    public Answer() {
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
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "answers=" + Arrays.toString(answer) +
                '}';
    }
}
