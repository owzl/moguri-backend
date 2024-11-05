package org.moguri.event.attendance.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.event.attendance.domain.Attendance;
import org.moguri.event.attendance.param.AttendanceCreateParam;
import org.moguri.event.attendance.param.AttendanceCreateReceivedParam;
import org.moguri.event.attendance.service.AttendanceService;
import org.moguri.event.quiz.controller.QuizController;
import org.moguri.event.quiz.param.QuizCreatePartParam;
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
    public ApiResponse<MoguriPage<Attendance>> getAttendances(AttendanceGetRequest request) {

        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Attendance> attendances = attendanceService.getAttendances(pageRequest);
        int totalCount = attendanceService.getTotalCount();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount,
                attendances.stream().map(AttendanceItem::of).toList()));

    }

    @GetMapping("/{attendanceId}")
    public ApiResponse<AttendanceItem> getAttendance(@PathVariable long attendanceId) {

        Attendance attendance = attendanceService.getAttendance(attendanceId);

        return ApiResponse.of(AttendanceItem.of(attendance));
    }

    @GetMapping("/member/{memberId}")
    public ApiResponse<List<AttendanceItem>> getMemberAttendances(@PathVariable long memberId) {
        List<Attendance> attendances = attendanceService.getMemberAttendances(memberId);
        List<AttendanceItem> items = attendances.stream()
                .map(AttendanceItem::of)
                .collect(Collectors.toList());
        return ApiResponse.of(items);
    }

    @GetMapping("/month/{memberId}")
    public ApiResponse<?> getMonthAttendances(@PathVariable long memberId) {
        int monthCount = attendanceService.getMonthCount(memberId);

        return ApiResponse.of(AttendanceMonthItem.of(monthCount));
    }

    @PostMapping("")
    public ApiResponse<ReturnCode> create(@RequestBody AttendanceCreateRequest request) {

        AttendanceCreateParam param = request.convert();
        attendanceService.createAttendance(param.toEntity());

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @GetMapping("/reward/{memberId}/{receivedType}")
    public ApiResponse<?> userReceivedCheck(@PathVariable long memberId, @PathVariable long receivedType) {
        boolean receivedCheck = attendanceService.userReceivedCheck(memberId, receivedType);
        return ApiResponse.of(ReceivedCheckItem.of(receivedCheck));
    }

    @PostMapping("/reward")
    public ApiResponse<ReturnCode> createReceived(@RequestBody AttendanceCreateReceivedRequest request) {

        AttendanceCreateReceivedParam param = request.convert();
        attendanceService.createAttendanceReceived(param.toEntity());

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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
    private static class AttendanceMonthItem {
        private int monthCount;

        private static AttendanceMonthItem of(Integer num) {
            AttendanceMonthItem converted = new AttendanceMonthItem();
            converted.setMonthCount(num);
            return converted;
        }
    }

    @Data
    public static class AttendanceCreateRequest {
        private long memberId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date attendanceDate;

        public AttendanceCreateParam convert() {
            return AttendanceCreateParam.builder()
                    .memberId(memberId)
                    .attendanceDate(attendanceDate)
                    .build();
        }
    }

    @Data
    private static class ReceivedCheckItem {
        private boolean receivedCheck;

        private static ReceivedCheckItem of(boolean received) {
            ReceivedCheckItem converted = new ReceivedCheckItem();
            converted.setReceivedCheck(received);
            return converted;
        }
    }

    @Data
    public static class AttendanceCreateReceivedRequest {
        private long memberId;
        private long receivedType;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date receivedDate;

        public AttendanceCreateReceivedParam convert() {
            return AttendanceCreateReceivedParam.builder()
                    .memberId(memberId)
                    .receivedType(receivedType)
                    .receivedDate(receivedDate)
                    .build();
        }
    }
}
