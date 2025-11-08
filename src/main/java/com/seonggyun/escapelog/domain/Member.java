package com.seonggyun.escapelog.domain;

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
    private static final Pattern ALLOWED = Pattern.compile("^[가-힣A-Za-z0-9]+$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Member(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("이름은 50자 이하로 입력해주세요.");
        }
        if (!ALLOWED.matcher(name).matches()) {
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

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : getClass().hashCode();
    }
}
