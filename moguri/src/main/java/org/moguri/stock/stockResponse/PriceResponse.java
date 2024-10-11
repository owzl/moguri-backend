package org.moguri.stock.stockResponse;

import lombok.Data;

@Data
public class PriceResponse {
    private Output output; // output 필드
    private String rt_cd;
    private String msg_cd;
    private String msg1;
}
