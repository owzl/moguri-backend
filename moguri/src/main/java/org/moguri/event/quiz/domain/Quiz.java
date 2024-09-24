package org.moguri.event.quiz.domain;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Data
public class Quiz {

    private long quizId;
    private String question;
    private int type;
    private String example1;
    private String example2;
    private String example3;
    private String example4;
    private String answer;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;


}
