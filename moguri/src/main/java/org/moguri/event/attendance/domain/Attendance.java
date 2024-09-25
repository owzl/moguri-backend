package org.moguri.event.attendance.domain;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Data
public class Attendance {

    private long attendanceId;
    private long memberId;
    private Date attendanceDate;
}
