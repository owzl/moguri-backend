package org.moguri.exception;

import lombok.Getter;
import org.moguri.common.enums.ReturnCode;

@Getter
public class MoguriException extends RuntimeException{
    private final ReturnCode returnCode;
    private final String returnMessage;

    public MoguriException(ReturnCode returnCode) {
        this.returnCode = returnCode;
        this.returnMessage = returnCode.getReturnMessage();
    }
}
