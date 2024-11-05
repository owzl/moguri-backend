package org.moguri.event.roulette.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moguri.event.roulette.domain.Roulette;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RouletteCreateParam {

    private long memberId;
    private Date rouletteDate;

    public Roulette toEntity() {
        Roulette roulette = Roulette.builder()
                .memberId(memberId)
                .rouletteDate(rouletteDate)
                .build();
        return roulette;
    }
}
