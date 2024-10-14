package org.moguri.event.roulette.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moguri.common.enums.ReturnCode;
import org.moguri.common.response.ApiResponse;
import org.moguri.event.roulette.domain.Roulette;
import org.moguri.event.roulette.param.RouletteCreateParam;
import org.moguri.event.roulette.service.RouletteService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roulette")
@RequiredArgsConstructor
public class RouletteController {

    private final RouletteService rouletteService;

    @GetMapping("/{memberId}")
    public ApiResponse<?> hasPlayedRouletteToday(@PathVariable long memberId) {
        boolean hasPlay = rouletteService.hasPlayedRouletteToday(memberId);
        return ApiResponse.of(RouletteHasItem.of(hasPlay));
    }

    @PostMapping("")
    public ApiResponse<ReturnCode> create(@RequestBody RouletteCreateRequest request) {

        RouletteCreateParam param = request.convert();
        rouletteService.createRoulette(param.toEntity());

        return ApiResponse.of(ReturnCode.SUCCESS);
    }

    @Data
    private static class RouletteHasItem {
        private boolean hasPlay;

        private static RouletteHasItem of(boolean has) {
            RouletteHasItem converted = new RouletteHasItem();
            converted.setHasPlay(has);
            return converted;
        }
    }

    @Data
    public static class RouletteCreateRequest {
        private long memberId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private Date rouletteDate;

        public RouletteCreateParam convert() {
            return RouletteCreateParam.builder()
                    .memberId(memberId)
                    .rouletteDate(rouletteDate)
                    .build();
        }
    }
}
