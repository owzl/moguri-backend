package org.moguri.event.attendance.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.event.attendance.domain.Attendance;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AttendanceMapper {

    List<Attendance> getAttendanceList();

    Optional<Attendance> getAttendance(long attendanceId);

    void createAttendance(Attendance attendance);

}