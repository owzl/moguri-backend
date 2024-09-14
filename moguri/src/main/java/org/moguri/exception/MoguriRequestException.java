package org.moguri.exception;

import org.moguri.common.enums.ReturnCode;

public class MoguriRequestException extends MoguriException {
    public MoguriRequestException(ReturnCode returnCode) {
        super(returnCode);
    }
}
