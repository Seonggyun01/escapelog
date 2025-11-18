package com.seonggyun.escapelog.domain.theme;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.theme.exception.ThemeErrorCode;
import com.seonggyun.escapelog.domain.theme.exception.ThemeException;
import com.seonggyun.escapelog.domain.venue.Venue;
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
            throw new ThemeException(ThemeErrorCode.VENUE_REQUIRED);
        }
    }

    private void validateTitle() {
        if (title == null || title.isBlank()) {
            throw new ThemeException(ThemeErrorCode.TITLE_EMPTY);
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new ThemeException(ThemeErrorCode.TITLE_LENGTH_INVALID);
        }
    }

    private void validateDifficulty() {
        if (difficulty == null) {
            throw new ThemeException(ThemeErrorCode.DIFFICULTY_REQUIRED);
        }
        if (difficulty < DIFFICULTY_MIN || difficulty > DIFFICULTY_MAX) {
            throw new ThemeException(ThemeErrorCode.DIFFICULTY_RANGE_INVALID);
        }
    }

    private void validateDuration() {
        if (durationMin == null) {
            throw new ThemeException(ThemeErrorCode.DURATION_REQUIRED);
        }
        if (durationMin < DURATION_MIN || durationMin > DURATION_MAX) {
            throw new ThemeException(ThemeErrorCode.DURATION_RANGE_INVALID);
        }
    }

    private void validatePlayers() {
        if (minPlayer == null || minPlayer < MIN_PLAYER_MIN) {
            throw new ThemeException(ThemeErrorCode.MIN_PLAYER_INVALID);
        }
        if (maxPlayer == null || maxPlayer < minPlayer) {
            throw new ThemeException(ThemeErrorCode.MAX_PLAYER_INVALID);
        }
    }

    private void validateGenre() {
        if (genres == null || genres.isEmpty()) {
            throw new ThemeException(ThemeErrorCode.GENRE_REQUIRED);
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
