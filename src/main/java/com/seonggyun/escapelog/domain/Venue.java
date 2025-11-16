package com.seonggyun.escapelog.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Venue {

    private static final int VENUE_NAME_MAX_LENGTH = 100;
    private static final int REGION_MAX_LENGTH = 60;

    private static final String ERROR_VENUE_NAME_EMPTY = "매장 이름은 비워둘 수 없습니다.";
    private static final String ERROR_VENUE_NAME_TOO_LONG_TEMPLATE = "매장 이름은 %d자 이하로 입력해야 합니다.";
    private static final String ERROR_REGION_TOO_LONG_TEMPLATE = "지역은 %d자 이하로 입력해야 합니다.";
    private static final String ERROR_THEME_NULL = "테마가 null일 수 없습니다.";
    private static final String ERROR_THEME_DUPLICATED = "이미 등록된 테마입니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long id;

    private String name;
    private String region;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Theme> themes = new ArrayList<>();

    public Venue(String name, String region) {
        this.name = name;
        this.region = region;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR_VENUE_NAME_EMPTY);
        }
        if (name.length() > VENUE_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_VENUE_NAME_TOO_LONG_TEMPLATE, VENUE_NAME_MAX_LENGTH)
            );
        }

        if (region != null && region.length() > REGION_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_REGION_TOO_LONG_TEMPLATE, REGION_MAX_LENGTH)
            );
        }
    }

    public void addTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException(ERROR_THEME_NULL);
        }
        if (themes.contains(theme)) {
            throw new IllegalArgumentException(ERROR_THEME_DUPLICATED);
        }
        themes.add(theme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venue)) {
            return false;
        }
        Venue venue = (Venue) o;
        return Objects.equals(id, venue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
