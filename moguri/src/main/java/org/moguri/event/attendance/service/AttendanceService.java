package org.moguri.event.attendance.service;

import org.moguri.common.response.PageRequest;
import org.moguri.event.attendance.domain.Attendance;


import java.util.List;

public interface AttendanceService {

    List<Attendance> getAttendances(PageRequest pageRequest);

    Attendance getAttendance(long no);

    void createAttendance(Attendance attendance);

    int getTotalCount();
}
