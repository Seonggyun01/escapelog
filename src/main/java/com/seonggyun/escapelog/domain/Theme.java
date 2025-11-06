package com.seonggyun.escapelog.domain;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

@Getter
public class Theme {
    private Long id;
    private Long venueId;
    private String title;
    private Integer difficulty;
    private Integer durationMin;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Set<Genre> genres = EnumSet.noneOf(Genre.class);

    public Theme(Long venueId, String title, Integer difficulty, Integer durationMin, Integer minPlayer,
                 Integer maxPlayer, Set<Genre> genres) {

        this.venueId = venueId;
        this.title = title;
        this.difficulty = difficulty;
        this.durationMin = durationMin;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.genres = genres;
        validate();
    }

    private void validate(){
        validateVenueId();
        validateTitle();
        validateDifficulty();
        validateDuration();
        validatePlayers();
        validateGenre();
    }

    private void validateVenueId() {
        if (venueId == null) {
            throw new IllegalArgumentException("테마는 매장에 속해야 합니다.");
        }
    }

    private void validateTitle() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("테마 제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 120) {
            throw new IllegalArgumentException("테마 제목은 120자 이하로 입력해야 합니다.");
        }
    }

    private void validateDifficulty() {
        if (difficulty == null) {
            throw new IllegalArgumentException("난이도는 필수입니다.");
        }
        if (difficulty < 1 || difficulty > 5) {
            throw new IllegalArgumentException("난이도는 1~5 사이여야 합니다.");
        }
    }

    private void validateDuration() {
        if (durationMin == null) {
            throw new IllegalArgumentException("플레이 시간은 필수입니다.");
        }
        if (durationMin < 0 || durationMin > 300) {
            throw new IllegalArgumentException("플레이 시간은 0~300분 사이여야 합니다.");
        }
    }

    private void validatePlayers() {
        if(minPlayer==null||minPlayer<1){
            throw new IllegalArgumentException("최소 인원은 1명 이상입니다.");
        }
        if (maxPlayer == null || maxPlayer < minPlayer) {
            throw new IllegalArgumentException("최대 인원은 최소 인원보다 작을 수 없습니다.");
        }
    }

    private void validateGenre(){
        if (genres == null || genres.isEmpty()) {
            throw new IllegalArgumentException("하나 이상의 장르가 필요합니다.");
        }
    }

    // 동일성 비교
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theme)) return false;
        Theme theme = (Theme) o;
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
