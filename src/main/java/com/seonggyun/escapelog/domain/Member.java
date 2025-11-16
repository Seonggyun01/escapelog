package com.seonggyun.escapelog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    private static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^[A-Za-z0-9!@#$%^&*()_+=-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9!@#$%^&*()_+=-]+$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣A-Za-z0-9]+$");

    private static final int LOGIN_ID_MIN_LENGTH = 4;
    private static final int LOGIN_ID_MAX_LENGTH = 20;

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 20;

    private static final int NAME_MAX_LENGTH = 50;

    private static final String ERROR_LOGIN_ID_EMPTY = "로그인 ID는 비어 있을 수 없습니다.";
    private static final String ERROR_LOGIN_ID_INVALID = "로그인 ID는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용할 수 있습니다.";
    private static final String ERROR_PASSWORD_EMPTY = "비밀번호는 비어 있을 수 없습니다.";
    private static final String ERROR_PASSWORD_INVALID = "비밀번호는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용할 수 있습니다.";
    private static final String ERROR_NAME_EMPTY = "이름은 비어 있을 수 없습니다.";
    private static final String ERROR_NAME_INVALID = "이름에는 한글, 영어, 숫자만 사용할 수 있습니다.";

    private static final String ERROR_LOGIN_ID_LENGTH_TEMPLATE = "로그인 ID는 %d자 이상 %d자 이하여야 합니다.";
    private static final String ERROR_PASSWORD_LENGTH_TEMPLATE = "비밀번호는 %d자 이상 %d자 이하여야 합니다.";
    private static final String ERROR_NAME_LENGTH_TEMPLATE = "이름은 %d자 이하로 입력해주세요.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        validate();
    }

    private void validate() {
        validateLoginId();
        validatePassword();
        validateName();
    }

    private void validateLoginId() {
        if (loginId == null || loginId.isBlank()) {
            throw new IllegalArgumentException(ERROR_LOGIN_ID_EMPTY);
        }

        int len = loginId.length();
        if (len < LOGIN_ID_MIN_LENGTH || len > LOGIN_ID_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_LOGIN_ID_LENGTH_TEMPLATE, LOGIN_ID_MIN_LENGTH, LOGIN_ID_MAX_LENGTH)
            );
        }

        if (!LOGIN_ID_PATTERN.matcher(loginId).matches()) {
            throw new IllegalArgumentException(ERROR_LOGIN_ID_INVALID);
        }
    }

    private void validatePassword() {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(ERROR_PASSWORD_EMPTY);
        }

        int len = password.length();
        if (len < PASSWORD_MIN_LENGTH || len > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_PASSWORD_LENGTH_TEMPLATE, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)
            );
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(ERROR_PASSWORD_INVALID);
        }
    }

    private void validateName() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR_NAME_EMPTY);
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_NAME_LENGTH_TEMPLATE, NAME_MAX_LENGTH)
            );
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(ERROR_NAME_INVALID);
        }
    }

    public void updateName(String name) {
        this.name = name;
        validate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        Member other = (Member) o;
        return id != null && id.equals(other.id);
    }
}
