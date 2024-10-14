package org.moguri.stock.stockResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Output {

    @JsonProperty("stck_prpr")
    private String stck_prpr;

    @JsonProperty("prdy_ctrt")
    private String prdy_ctrt;
}
