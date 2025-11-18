package com.seonggyun.escapelog.service.theme.exception;

import com.seonggyun.escapelog.common.BaseException;

public class ThemeServiceException extends BaseException {
    public ThemeServiceException(ThemeServiceErrorCode errorCode) {
        super(errorCode);
    }
}
