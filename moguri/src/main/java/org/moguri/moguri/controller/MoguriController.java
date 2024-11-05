package org.moguri.moguri.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.common.response.MoguriPage;
import org.moguri.common.response.PageRequest;
import org.moguri.common.validator.PageLimitSizeValidator;
import org.moguri.moguri.domain.Moguri;
import org.moguri.moguri.service.MoguriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moguri")
@RequiredArgsConstructor
public class MoguriController {

    private final MoguriService moguriService;

    @GetMapping
    public ApiResponse<?> getAllMoguri(MoguriGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Moguri> moguriList = moguriService.getAllMoguri(pageRequest);
        int totalCount = moguriList.size();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount, moguriList.stream().map(MoguriItem::of).toList()));
    }

    @GetMapping("/{memberId}")
    public ApiResponse<?> getAllUserMoguri(@PathVariable("memberId") Long memberId, MoguriGetRequest request) {
        PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());

        List<Moguri> moguriList = moguriService.getAllUserMoguri(pageRequest, memberId);
        int totalCount = moguriList.size();

        return ApiResponse.of(MoguriPage.of(pageRequest, totalCount, moguriList.stream().map(MoguriItem::of).toList()));
    }

    @PostMapping("/{moguriId}/{memberId}")
    public ApiResponse<?> purchaseMoguri(@PathVariable("moguriId") Long moguriId, @PathVariable("memberId") Long memberId) {
        moguriService.purchaseMoguri(moguriId, memberId);
        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    private static class MoguriGetRequest {
        private int page = 0;
        private int limit = 12; // default 값
    }

    @Data
    private static class MoguriItem {
        private Long moguriId; // moguriId 추가
        private String moguriName;
        private int moguriPrice;
        private String filePath;

        private static MoguriItem of(Moguri moguri) {
            MoguriItem converted = new MoguriItem();
            converted.moguriId = moguri.getMoguriId(); // moguriId 할당
            converted.moguriName = moguri.getMoguriName();
            converted.moguriPrice = moguri.getMoguriPrice();
            converted.filePath = moguri.getFilePath();
            return converted;
        }
    }

    @Data
    private static class MoguriUserItem {
        private String moguriName;
        private String filePath;

        private static MoguriUserItem of(Moguri moguri) {
            MoguriUserItem converted = new MoguriUserItem();
            converted.moguriName = moguri.getMoguriName();
            converted.filePath = moguri.getFilePath();
            return converted;
        }
    }
}
