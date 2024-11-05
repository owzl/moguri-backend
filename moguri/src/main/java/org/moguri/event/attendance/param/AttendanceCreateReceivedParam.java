package org.moguri.event.attendance.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.event.attendance.domain.AttendanceReceived;
import org.moguri.event.quiz.domain.QuizPart;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AttendanceCreateReceivedParam {

    private long memberId;
    private long receivedType;
    private Date receivedDate;

    public AttendanceReceived toEntity() {
        AttendanceReceived attendanceReceived = AttendanceReceived.builder()
                .memberId(memberId)
                .receivedType(receivedType)
                .receivedDate(receivedDate)
                .build();
        return attendanceReceived;
    }
}
