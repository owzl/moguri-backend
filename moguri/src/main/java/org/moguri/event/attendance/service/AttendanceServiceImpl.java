package org.moguri.event.attendance.service;

import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.repository.AttendanceMapper;
import org.moguri.exception.MoguriLogicException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

    private final AttendanceMapper attendanceMapper;

    @Override
    public List<Attendance> getAttendanceList() {
        return attendanceMapper.getAttendanceList();
    }

    @Override
    public Attendance getAttendance(long attendanceId) {

        return attendanceMapper.getAttendance(attendanceId)
                .orElseThrow(() -> new MoguriLogicException(ReturnCode.NOT_FOUND_ENTITY));
    }

    @Override
    public void createAttendance(Attendance attendance) {
        try {
            attendanceMapper.createAttendance(attendance);
        } catch (Exception e) {
            throw new MoguriLogicException(ReturnCode.WRONG_PARAMETER);
        }
    }
}
