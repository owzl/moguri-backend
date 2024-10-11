package org.moguri.event.attendance.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.domain.AttendanceReceived;
import org.moguri.event.quiz.domain.QuizPart;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    int getTotalCount();

    List<Attendance> getAttendances(PageRequest pageRequest);

    Attendance getAttendance(long attendanceId);

    List<Attendance> getMemberAttendances(long memberId);

    void createAttendance(Attendance attendance);

    int getMonthCount(long memberId);

    boolean userReceivedCheck(@Param("memberId") long memberId, @Param("receivedType") long receivedType);

    void createAttendanceReceived(AttendanceReceived attendanceReceived);
}