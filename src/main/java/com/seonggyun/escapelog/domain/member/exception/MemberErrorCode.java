package com.seonggyun.escapelog.domain.member.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    LOGIN_ID_EMPTY(HttpStatus.BAD_REQUEST, "MEMBER-001", "로그인 ID는 비어 있을 수 없습니다."),
    LOGIN_ID_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-002", "로그인 ID는 4자 이상 20자 이하여야 합니다."),
    LOGIN_ID_FORMAT_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-003", "로그인 ID는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용할 수 있습니다."),
    PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "MEMBER-004", "비밀번호는 비어 있을 수 없습니다."),
    PASSWORD_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-005", "비밀번호는 6자 이상 20자 이하여야 합니다."),
    PASSWORD_FORMAT_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-006", "비밀번호는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용할 수 있습니다."),
    NAME_EMPTY(HttpStatus.BAD_REQUEST, "MEMBER-007", "이름은 비어 있을 수 없습니다."),
    NAME_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-008", "이름은 50자 이하로 입력해주세요."),
    NAME_FORMAT_INVALID(HttpStatus.BAD_REQUEST, "MEMBER-009", "이름에는 한글, 영어, 숫자만 사용할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
