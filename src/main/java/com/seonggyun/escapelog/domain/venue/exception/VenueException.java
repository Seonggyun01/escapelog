package com.seonggyun.escapelog.domain.venue.exception;

import com.seonggyun.escapelog.common.BaseException;

public class VenueException extends BaseException {
    public VenueException(VenueErrorCode errorCode){
        super(errorCode);
    }
}
