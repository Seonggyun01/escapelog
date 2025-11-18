package com.seonggyun.escapelog.domain.playRecord.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlayRecordErrorCode implements ErrorCode {
    MEMBER_REQUIRED(HttpStatus.BAD_REQUEST, "PLAY_RECORD-001", "회원 정보가 누락되었습니다."),
    THEME_REQUIRED(HttpStatus.BAD_REQUEST, "PLAY_RECORD-002", "테마 정보가 누락되었습니다."),
    PLAY_DATE_REQUIRED(HttpStatus.BAD_REQUEST,"PLAY_RECORD-003", "플레이 날짜는 필수입니다."),
    PLAY_DATE_IN_FUTURE(HttpStatus.BAD_REQUEST, "PLAY_RECORD-004","플레이 날짜는 미래일 수 없습니다."),
    CLEARED_REQUIRED(HttpStatus.BAD_REQUEST,"PLAY_RECORD-005", "성공 여부는 필수입니다."),
    CLEAR_TIME_REQUIRED(HttpStatus.BAD_REQUEST,"PLAY_RECORD-006", "성공한 기록에는 유효한 클리어 시간이 필요합니다."),
    CLEAR_TIME_FORBIDDEN(HttpStatus.BAD_REQUEST, "PLAY_RECORD-007", "실패한 기록에는 클리어 시간을 입력할 수 없습니다."),
    HINT_COUNT_NEGATIVE(HttpStatus.BAD_REQUEST, "PLAY_RECORD-008", "힌트 사용 개수는 음수일 수 없습니다."),
    RATING_RANGE_INVALID(HttpStatus.BAD_REQUEST,"PLAY_RECORD-009", "평점은 1~5점 사이여야 합니다."),
    COMMENT_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "PLAY_RECORD-010", "후기는 500자 이하로 입력해주세요.");

    private final HttpStatus Status;
    private final String code;
    private final String message;
}
