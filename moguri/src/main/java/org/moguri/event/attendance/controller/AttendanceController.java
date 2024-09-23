package org.moguri.event.attendance.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.param.AttendanceCreateParam;
import org.moguri.event.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("")
    public ApiResponse<List<AttendanceItem>> getAttendanceList() {
        List<Attendance> Attendances = attendanceService.getAttendanceList();
        List<AttendanceItem> items = Attendances.stream()
                .map(AttendanceItem::of)
                .collect(Collectors.toList());
        return ApiResponse.of(items);
    }

    @GetMapping("/{attendanceId}")
    public ApiResponse<AttendanceItem> getAttendance(@PathVariable long attendanceId) {

        Attendance attendance = attendanceService.getAttendance(attendanceId);

        return ApiResponse.of(AttendanceItem.of(attendance));
    }

    @PostMapping
    public ApiResponse<ReturnCode> create(@RequestBody AttendanceCreateRequest request) {
        AttendanceCreateParam param = request.convert();
        attendanceService.createAttendance(param.toEntity());
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    private static class AttendanceItem {

        private long attendance_id;
        private long member_id;
        private Date attendance_date;

        private static AttendanceItem of(Attendance attendance) {
            AttendanceItem converted = new AttendanceItem();
            converted.setAttendance_id(attendance.getAttendance_id());
            converted.setMember_id(attendance.getMember_id());
            converted.setAttendance_date(attendance.getAttendance_date());
            return converted;
        }
    }

    @Data
    public static class AttendanceCreateRequest {
        private long member_id;
        private Date attendance_date;

        public AttendanceCreateParam convert() {
            return AttendanceCreateParam.builder()
                    .member_id(member_id)
                    .attendance_date(attendance_date)
                    .build();
        }
    }

}
