package com.seonggyun.escapelog.domain.member;

import com.seonggyun.escapelog.domain.member.exception.MemberErrorCode;
import com.seonggyun.escapelog.domain.member.exception.MemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

//    @Column(name = "created_at", nullable = false)
//    @CreatedDate
//    private LocalDateTime createdAt;

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
            throw new MemberException(MemberErrorCode.LOGIN_ID_EMPTY);
        }

        int len = loginId.length();
        if (len < LOGIN_ID_MIN_LENGTH || len > LOGIN_ID_MAX_LENGTH) {
            throw new MemberException(MemberErrorCode.LOGIN_ID_LENGTH_INVALID);
        }

        if (!LOGIN_ID_PATTERN.matcher(loginId).matches()) {
            throw new MemberException(MemberErrorCode.LOGIN_ID_FORMAT_INVALID);
        }
    }

    private void validatePassword() {
        if (password == null || password.isBlank()) {
            throw new MemberException(MemberErrorCode.PASSWORD_EMPTY);
        }

        int len = password.length();
        if (len < PASSWORD_MIN_LENGTH || len > PASSWORD_MAX_LENGTH) {
            throw new MemberException(MemberErrorCode.PASSWORD_LENGTH_INVALID);
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new MemberException(MemberErrorCode.PASSWORD_FORMAT_INVALID);
        }
    }

    private void validateName() {
        if (name == null || name.isBlank()) {
            throw new MemberException(MemberErrorCode.NAME_EMPTY);
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new MemberException(MemberErrorCode.NAME_LENGTH_INVALID);
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new MemberException(MemberErrorCode.NAME_FORMAT_INVALID);
        }
    }

    public void updateName(String name) {
        this.name = name;
        validateName();
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
