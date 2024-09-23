package org.moguri.event.attendance.domain;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Data
public class Attendance {

    private long attendance_id;
    private long member_id;
    private Date attendance_date;


}
