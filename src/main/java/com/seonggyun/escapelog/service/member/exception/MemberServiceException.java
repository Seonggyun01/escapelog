package com.seonggyun.escapelog.service.member.exception;

import com.seonggyun.escapelog.common.BaseException;

public class MemberServiceException extends BaseException {
    public MemberServiceException(MemberServiceErrorCode errorCode) {
        super(errorCode);
    }
}
