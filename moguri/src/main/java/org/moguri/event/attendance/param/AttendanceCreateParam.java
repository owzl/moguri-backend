package org.moguri.event.attendance.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.event.attendance.domain.Attendance;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AttendanceCreateParam {

    private long memberId;
    private Date attendanceDate;

    public Attendance toEntity() {
        Attendance attendance = Attendance.builder()
                .memberId(memberId)
                .attendanceDate(attendanceDate)
                .build();
        return attendance;
    }
}
