package org.moguri.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    SUCCESS("0000", "Success"),

    WRONG_PARAMETER("4000", "Wrong parameter"),
    NOT_FOUND_ENTITY("4001", "Not found"),
    INVALID_PERIOD_TYPE("4002", "Invalid period type");

    private final String returnCode;
    private final String returnMessage;
}
