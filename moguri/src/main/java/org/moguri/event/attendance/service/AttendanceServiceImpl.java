package org.moguri.event.attendance.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.PageRequest;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.domain.AttendanceReceived;
import org.moguri.event.attendance.repository.AttendanceMapper;
import org.moguri.event.quiz.domain.QuizPart;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

    private final AttendanceMapper attendanceMapper;

    public int getTotalCount() {
        return attendanceMapper.getTotalCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Attendance> getAttendances(PageRequest pageRequest) {
        return attendanceMapper.getAttendances(pageRequest);
    }

    @Override
    public Attendance getAttendance(long attendanceId) {
        Attendance attendance = Optional.ofNullable(attendanceMapper.getAttendance(attendanceId)).orElseThrow(() -> new
                MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
        return attendance;
    }

    @Override
    public List<Attendance> getMemberAttendances(long memberId) {
        return attendanceMapper.getMemberAttendances(memberId);
    }

    @Override
    public void createAttendance(Attendance attendance) {
        try {
            attendanceMapper.createAttendance(attendance);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }

    @Override
    public int getMonthCount(long memberId) {
        return attendanceMapper.getMonthCount(memberId);
    }

    @Override
    public boolean userReceivedCheck(long memberId, long receivedType) {
        return attendanceMapper.userReceivedCheck(memberId, receivedType);
    }

    @Override
    public void createAttendanceReceived(AttendanceReceived attendanceReceived) {
        try {
            attendanceMapper.createAttendanceReceived(attendanceReceived);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }
}
