package org.moguri.event.attendance.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.event.attendance.domain.Attendance;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    int getTotalCount();

    List<Attendance> getAttendances(PageRequest pageRequest);

    Attendance getAttendance(long attendanceId);

    void createAttendance(Attendance attendance);
}