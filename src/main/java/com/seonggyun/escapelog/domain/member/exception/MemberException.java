package com.seonggyun.escapelog.domain.member.exception;

import com.seonggyun.escapelog.common.BaseException;

public class MemberException extends BaseException {
    public MemberException(MemberErrorCode errorCode){
        super(errorCode);
    }
}
