package com.seonggyun.escapelog.domain.venue.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VenueErrorCode implements ErrorCode {
    VENUE_NAME_EMPTY(HttpStatus.BAD_REQUEST, "VENUE-001", "매장 이름은 비워둘 수 없습니다."),
    VENUE_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "VENUE-002", "매장 이름은 100자 이하로 입력해야 합니다."),
    REGION_TOO_LONG(HttpStatus.BAD_REQUEST,"VENUE-003",	"지역은 60자 이하로 입력해야 합니다."),
    THEME_NULL(HttpStatus.BAD_REQUEST, "VENUE-004", "테마가 null일 수 없습니다."),
    THEME_DUPLICATE(HttpStatus.BAD_REQUEST, "VENUE-005", "이미 등록된 테마입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
