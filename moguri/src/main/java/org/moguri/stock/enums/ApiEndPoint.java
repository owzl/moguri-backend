package org.moguri.stock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiEndPoint {
    GET_TOKEN("/oauth2/tokenP"),
    GET_CHART("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice"),
    GET_PRESENT_PRICE("/uapi/domestic-stock/v1/quotations/inquire-price");

    private final String path;
}
