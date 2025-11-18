package com.seonggyun.escapelog.service.playRecord.exception;

import com.seonggyun.escapelog.common.BaseException;

public class PlayRecordServiceException extends BaseException {
    public PlayRecordServiceException(PlayRecordServiceErrorCode errorCode) {
        super(errorCode);
    }
}
