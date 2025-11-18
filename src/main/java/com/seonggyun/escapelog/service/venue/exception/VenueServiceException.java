package com.seonggyun.escapelog.service.venue.exception;

import com.seonggyun.escapelog.common.BaseException;

public class VenueServiceException extends BaseException {
    public VenueServiceException(VenueServiceErrorCode errorCode){
        super(errorCode);
    }
}
