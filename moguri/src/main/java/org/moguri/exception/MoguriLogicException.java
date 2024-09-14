package org.moguri.exception;

import org.moguri.common.enums.ReturnCode;

public class MoguriLogicException extends MoguriException {
    public MoguriLogicException(ReturnCode returnCode) {
        super(returnCode);
    }
}
