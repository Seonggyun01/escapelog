package com.seonggyun.escapelog.service.theme.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ThemeServiceErrorCode implements ErrorCode {
    VENUE_NOT_FOUND(HttpStatus.NOT_FOUND, "THEME-001", "해당 매장을 찾을 수 없습니다."),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "THEME-002", "해당 테마를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
