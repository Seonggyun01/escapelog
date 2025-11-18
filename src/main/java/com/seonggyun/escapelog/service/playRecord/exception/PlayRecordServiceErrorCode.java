package com.seonggyun.escapelog.service.playRecord.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlayRecordServiceErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAYRECORD-001", "해당 회원을 찾을 수 없습니다."),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAYRECORD-002", "해당 테마를 찾을 수 없습니다."),
    PLAY_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "PLAYRECORD-003", "해당 플레이 기록을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
