package com.seonggyun.escapelog.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Venue {
    private Long id;
    private String name;
    private String region;
    private List<Theme> themes = new ArrayList<>();

    public Venue(String name, String region) {
        this.name = name;
        this.region = region;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("매장 이름은 비워둘 수 없습니다.");
        }
        if (name.length() >= 100) {
            throw new IllegalArgumentException("매장 이름은 120자 이하로 입력해야 합니다.");
        }

        if (region != null && region.length() > 60) {
            throw new IllegalArgumentException("지역은 60자 이하로 입력해야 합니다.");
        }
    }

    public void addTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("테마가 nulld일 수 없습니다.");
        }
        if (themes.contains(theme)) {
            throw new IllegalArgumentException("이미 등록된 테마입니다.");
        }
        themes.add(theme);
    }


    // 동일성 비교
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
