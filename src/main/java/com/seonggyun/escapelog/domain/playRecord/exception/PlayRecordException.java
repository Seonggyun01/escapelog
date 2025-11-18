package com.seonggyun.escapelog.domain.playRecord.exception;

import com.seonggyun.escapelog.common.BaseException;

public class PlayRecordException extends BaseException {
    public PlayRecordException(PlayRecordErrorCode errorCode){
        super(errorCode);
    }
}
