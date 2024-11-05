package org.moguri.stock.stockResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockChart {
    private String stckBsopDate; // 영업 일자
    private String stckClpr; // 종가
    private String stckOprc; // 시가
    private String stckHgpr; // 최고가
    private String stckLwpr; // 최저가
    private String acmlVol; // 누적 거래량
}
