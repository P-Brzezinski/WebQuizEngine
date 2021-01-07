package pl.brzezinski.web_quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto {
        private Long id;
        private String title;
        private String text;
        private List<String> options;
        private List<Integer> answer;
}
