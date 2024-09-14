package org.moguri.common.validator;

import org.moguri.common.enums.ReturnCode;
import org.moguri.exception.MoguriRequestException;

public class PageLimitSizeValidator {
    public static void validateSize(int page, int limit, int maxLimitSize) {
        if (page < 0 || limit <= 0 || limit > maxLimitSize) {
            throw new MoguriRequestException(ReturnCode.WRONG_PARAMETER);
        }
    }
}
