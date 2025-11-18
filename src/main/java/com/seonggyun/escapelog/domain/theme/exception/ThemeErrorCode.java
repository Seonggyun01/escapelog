package com.seonggyun.escapelog.domain.theme.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ThemeErrorCode implements ErrorCode {
    VENUE_REQUIRED(	HttpStatus.BAD_REQUEST,"THEME-001", "테마는 매장에 속해야 합니다."),
    TITLE_EMPTY(HttpStatus.BAD_REQUEST, "THEME-002", "테마 제목은 비어 있을 수 없습니다."),
    TITLE_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "THEME-003","테마 제목은 100자 이하로 입력해야 합니다."),
    DIFFICULTY_REQUIRED(HttpStatus.BAD_REQUEST, "THEME-004", "난이도는 필수입니다."),
    DIFFICULTY_RANGE_INVALID(HttpStatus.BAD_REQUEST, "THEME-005", "난이도는 1~5 사이여야 합니다."),
    DURATION_REQUIRED(HttpStatus.BAD_REQUEST, "THEME-006", "플레이 시간은 필수입니다."),
    DURATION_RANGE_INVALID(HttpStatus.BAD_REQUEST,"THEME-007", "플레이 시간은 0~300분 사이여야 합니다."),
    MIN_PLAYER_INVALID(	HttpStatus.BAD_REQUEST, "THEME-008", "최소 인원은 1명 이상입니다."),
    MAX_PLAYER_INVALID(HttpStatus.BAD_REQUEST, "THEME-009", "최대 인원은 최소 인원보다 작을 수 없습니다."),
    GENRE_REQUIRED(HttpStatus.BAD_REQUEST, "THEME-010", "하나 이상의 장르가 필요합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
