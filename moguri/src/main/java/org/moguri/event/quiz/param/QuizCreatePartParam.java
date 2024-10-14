package org.moguri.event.quiz.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.event.quiz.domain.QuizPart;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QuizCreatePartParam {

    private long memberId;
    private long quizType;
    private Date partDate;

    public QuizPart toEntity() {
        QuizPart quizpart = QuizPart.builder()
                .memberId(memberId)
                .quizType(quizType)
                .partDate(partDate)
                .build();
        return quizpart;
    }
}
