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
    private static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^[A-Za-z0-9!@#$%^&*()_+=-]{4,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9!@#$%^&*()_+=-]{6,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣A-Za-z0-9]+$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;  //실제 서비스는 암호화 필수!

    @Column(nullable = false)
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
            throw new IllegalArgumentException("로그인 ID는 비어 있을 수 없습니다.");
        }
        if (!LOGIN_ID_PATTERN.matcher(loginId).matches()) {
            throw new IllegalArgumentException("로그인 ID는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용하며 4~20자여야 합니다.");
        }
    }

    private void validatePassword() {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자(!@#$%^&*()_+=-)만 사용하며 6~20자여야 합니다.");
        }
    }

    private void validateName() {
        if (name == null || name.isBlank())
        {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (name.length() > 50)
        {
            throw new IllegalArgumentException("이름은 50자 이하로 입력해주세요.");
        }
        if (!NAME_PATTERN.matcher(name).matches())
        {
            throw new IllegalArgumentException("이름에는 한글, 영어, 숫자만 사용할 수 있습니다.");
        }
    }

    public void updateName(String name) {
        this.name = name;
        validate();
    }

    // 동일성 비교 (id 기준)
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
