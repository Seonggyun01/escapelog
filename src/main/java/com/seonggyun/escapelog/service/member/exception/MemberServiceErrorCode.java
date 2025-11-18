package com.seonggyun.escapelog.service.member.exception;

import com.seonggyun.escapelog.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberServiceErrorCode implements ErrorCode {
    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "MEMBER-S-001", "이미 사용 중인 로그인 ID입니다."),
    DUPLICATE_NAME(HttpStatus.CONFLICT, "MEMBER-S-002", "이미 사용 중인 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-S-003", "존재하지 않는 회원입니다."),
    MEMBER_NOT_FOUND_BY_NAME(HttpStatus.NOT_FOUND, "MEMBER-S-004", "해당 닉네임의 회원이 없습니다."),
    MEMBER_NOT_FOUND_BY_LOGIN_ID(HttpStatus.NOT_FOUND, "MEMBER-S-005", "해당 ID의 회원이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER-S-006", "아이디 또는 비밀번호가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
