package org.moguri.event.attendance.service;

import org.moguri.event.attendance.domain.Attendance;


import java.util.List;

public interface AttendanceService {

    List<Attendance> getAttendanceList();

    Attendance getAttendance(long no);

    void createAttendance(Attendance attendance);
}
