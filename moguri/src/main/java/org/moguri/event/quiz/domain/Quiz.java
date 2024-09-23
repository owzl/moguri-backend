package org.moguri.event.quiz.domain;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Data
public class Quiz {

    private long quiz_id;
    private String question;
    private int type;
    private String example1;
    private String example2;
    private String example3;
    private String example4;
    private String answer;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;


}
