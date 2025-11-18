package com.seonggyun.escapelog.domain.theme.exception;

import com.seonggyun.escapelog.common.BaseException;

public class ThemeException extends BaseException {
    public ThemeException(ThemeErrorCode errorCode){
        super(errorCode);
    }
}
