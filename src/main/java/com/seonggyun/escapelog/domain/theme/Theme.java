package com.seonggyun.escapelog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theme {
    private static final int TITLE_MAX_LENGTH = 100;

    private static final int DIFFICULTY_MIN = 1;
    private static final int DIFFICULTY_MAX = 5;

    private static final int DURATION_MIN = 0;
    private static final int DURATION_MAX = 300;

    private static final int MIN_PLAYER_MIN = 1;

    private static final String ERROR_VENUE_REQUIRED = "테마는 매장에 속해야 합니다.";
    private static final String ERROR_TITLE_EMPTY = "테마 제목은 비어 있을 수 없습니다.";
    private static final String ERROR_TITLE_LENGTH_TEMPLATE = "테마 제목은 %d자 이하로 입력해야 합니다.";
    private static final String ERROR_DIFFICULTY_REQUIRED = "난이도는 필수입니다.";
    private static final String ERROR_DIFFICULTY_RANGE_TEMPLATE = "난이도는 %d~%d 사이여야 합니다.";
    private static final String ERROR_DURATION_REQUIRED = "플레이 시간은 필수입니다.";
    private static final String ERROR_DURATION_RANGE_TEMPLATE = "플레이 시간은 %d~%d분 사이여야 합니다.";
    private static final String ERROR_MIN_PLAYER_INVALID = "최소 인원은 1명 이상입니다.";
    private static final String ERROR_MAX_PLAYER_INVALID = "최대 인원은 최소 인원보다 작을 수 없습니다.";
    private static final String ERROR_GENRE_REQUIRED = "하나 이상의 장르가 필요합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    private String title;
    private Integer difficulty;
    private Integer durationMin;
    private Integer minPlayer;
    private Integer maxPlayer;

    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres = EnumSet.noneOf(Genre.class);

    public Theme(Venue venue, String title, Integer difficulty, Integer durationMin,
                 Integer minPlayer, Integer maxPlayer, Set<Genre> genres) {

        this.venue = venue;
        this.title = title;
        this.difficulty = difficulty;
        this.durationMin = durationMin;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.genres = EnumSet.copyOf(genres);
        validate();
        venue.addTheme(this);
    }

    private void validate() {
        validateVenue();
        validateTitle();
        validateDifficulty();
        validateDuration();
        validatePlayers();
        validateGenre();
    }

    private void validateVenue() {
        if (venue == null) {
            throw new IllegalArgumentException(ERROR_VENUE_REQUIRED);
        }
    }

    private void validateTitle() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(ERROR_TITLE_EMPTY);
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_TITLE_LENGTH_TEMPLATE, TITLE_MAX_LENGTH)
            );
        }
    }

    private void validateDifficulty() {
        if (difficulty == null) {
            throw new IllegalArgumentException(ERROR_DIFFICULTY_REQUIRED);
        }
        if (difficulty < DIFFICULTY_MIN || difficulty > DIFFICULTY_MAX) {
            throw new IllegalArgumentException(
                    String.format(ERROR_DIFFICULTY_RANGE_TEMPLATE,
                            DIFFICULTY_MIN, DIFFICULTY_MAX)
            );
        }
    }

    private void validateDuration() {
        if (durationMin == null) {
            throw new IllegalArgumentException(ERROR_DURATION_REQUIRED);
        }
        if (durationMin < DURATION_MIN || durationMin > DURATION_MAX) {
            throw new IllegalArgumentException(
                    String.format(ERROR_DURATION_RANGE_TEMPLATE,
                            DURATION_MIN, DURATION_MAX)
            );
        }
    }

    private void validatePlayers() {
        if (minPlayer == null || minPlayer < MIN_PLAYER_MIN) {
            throw new IllegalArgumentException(ERROR_MIN_PLAYER_INVALID);
        }
        if (maxPlayer == null || maxPlayer < minPlayer) {
            throw new IllegalArgumentException(ERROR_MAX_PLAYER_INVALID);
        }
    }

    private void validateGenre() {
        if (genres == null || genres.isEmpty()) {
            throw new IllegalArgumentException(ERROR_GENRE_REQUIRED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        Theme theme = (Theme) o;
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
