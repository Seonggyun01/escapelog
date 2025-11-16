package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberSignUpForm {

    private static final String ERROR_LOGIN_ID_REQUIRED = "로그인 ID를 입력해주세요.";
    private static final String ERROR_PASSWORD_REQUIRED = "비밀번호를 입력해주세요.";
    private static final String ERROR_NAME_REQUIRED = "이름을 입력해주세요.";

    private static final String LOGIN_ID_REGEX = "^[A-Za-z0-9!@#$%^&*()_+=-]+$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9!@#$%^&*()_+=-]+$";
    private static final String NAME_REGEX = "^[가-힣A-Za-z0-9]+$";

    private static final String ERROR_LOGIN_ID_PATTERN = "로그인 ID 형식이 올바르지 않습니다.";
    private static final String ERROR_PASSWORD_PATTERN = "비밀번호 형식이 올바르지 않습니다.";
    private static final String ERROR_NAME_PATTERN = "이름 형식이 올바르지 않습니다.";

    @NotBlank(message = ERROR_LOGIN_ID_REQUIRED)
    @Pattern(regexp = LOGIN_ID_REGEX, message = ERROR_LOGIN_ID_PATTERN)
    private String loginId;

    @NotBlank(message = ERROR_PASSWORD_REQUIRED)
    @Pattern(regexp = PASSWORD_REGEX, message = ERROR_PASSWORD_PATTERN)
    private String password;

    @NotBlank(message = ERROR_NAME_REQUIRED)
    @Pattern(regexp = NAME_REGEX, message = ERROR_NAME_PATTERN)
    private String name;
}
