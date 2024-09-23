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

    private long member_id;
    private Date attendance_date;

    public Attendance toEntity() {
        Attendance attendance = Attendance.builder()
                .member_id(member_id)
                .attendance_date(attendance_date)
                .build();
        return attendance;
    }
}
