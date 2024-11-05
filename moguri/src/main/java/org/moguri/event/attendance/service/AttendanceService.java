package org.moguri.event.attendance.service;

import org.moguri.common.response.PageRequest;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.domain.AttendanceReceived;


import java.util.List;

public interface AttendanceService {

    List<Attendance> getAttendances(PageRequest pageRequest);

    Attendance getAttendance(long no);

    List<Attendance> getMemberAttendances(long memberId);

    void createAttendance(Attendance attendance);

    int getTotalCount();

    int getMonthCount(long memberId);

    boolean userReceivedCheck(long memberId,long receivedType);

    void createAttendanceReceived(AttendanceReceived attendanceReceived);
}
