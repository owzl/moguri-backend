package org.moguri.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    SUCCESS("0000", "Success"),

    WRONG_PARAMETER("4000", "Wrong parameter"),
    NOT_FOUND_ENTITY("4001", "Not found"),
    INVALID_PERIOD_TYPE("4002", "Invalid period type"),
    NOT_ENOUGH_COTTON_CANDY("4003", "Not enough cottoncandy"),
    NOT_ENOUGH_STOCKS("4004", "Not enough stocks"),
    INVALID_TRADE_TYPE("4005", "Invalid trade type");

    private final String returnCode;
    private final String returnMessage;
}
