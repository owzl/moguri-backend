package org.moguri.event.quiz.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.event.quiz.domain.Quiz;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuizCreateParam {

    private String question;
    private int type;
    private String example1;
    private String example2;
    private String example3;
    private String example4;
    private String answer;

    public Quiz toEntity() {
        Quiz quiz = Quiz.builder()
                .question(question)
                .type(type)
                .example1(example1)
                .example2(example2)
                .example3(example3)
                .example4(example4)
                .answer(answer)
                .build();
        return quiz;
    }
}
