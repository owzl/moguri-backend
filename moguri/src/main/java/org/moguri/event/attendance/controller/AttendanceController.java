package org.moguri.event.attendance.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.param.AttendanceCreateParam;
import org.moguri.event.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("")
    public ApiResponse<MoguriPage<Attendance>> getAttendances(AttendanceGetRequest request) {

        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), request.getLimit());

        List<Attendance> Attendances = attendanceService.getAttendances(pageRequest);
        int totalCount = attendanceService.getTotalCount();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                Attendances.stream().map(AttendanceItem::of).toList()));

    }

    @GetMapping("/{attendanceId}")
    public ApiResponse<AttendanceItem> getAttendance(@PathVariable long attendanceId) {

        Attendance attendance = attendanceService.getAttendance(attendanceId);

        return ApiResponse.of(AttendanceItem.of(attendance));
    }

    @PostMapping("")
    public ApiResponse<ReturnCode> create(@RequestBody AttendanceCreateRequest request) {

        AttendanceCreateParam param = request.convert();
        attendanceService.createAttendance(param.toEntity());

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    public static class AttendanceGetRequest {

        private int page = 0;
        private int limit = 30;
        //default 값
    }

    @Data
    private static class AttendanceItem {
        private long attendanceId;
        private long memberId;
        private Date attendanceDate;

        private static AttendanceItem of(Attendance attendance) {
            AttendanceItem converted = new AttendanceItem();
            converted.setAttendanceId(attendance.getAttendanceId());
            converted.setMemberId(attendance.getMemberId());
            converted.setAttendanceDate(attendance.getAttendanceDate());
            return converted;
        }
    }

    @Data
    public static class AttendanceCreateRequest {
        private long memberId;
        private Date attendanceDate;

        public AttendanceCreateParam convert() {
            return AttendanceCreateParam.builder()
                    .memberId(memberId)
                    .attendanceDate(attendanceDate)
                    .build();
        }
    }
}
