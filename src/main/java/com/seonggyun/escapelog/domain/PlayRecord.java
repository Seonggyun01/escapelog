package com.seonggyun.escapelog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    private LocalDate playDate;
    private Boolean cleared;
    private int clearTimeSec;
    private int hintCount = 0;
    private int rating;
    private String comment;

    public PlayRecord(Member member, Theme theme, LocalDate playDate, Boolean cleared, int clearTimeSec, int hintCount,
                      int rating, String comment) {
        this.member = member;
        this.theme = theme;
        this.playDate = playDate;
        this.cleared = cleared;
        this.clearTimeSec = clearTimeSec;
        this.hintCount = hintCount;
        this.rating = rating;
        this.comment = comment;

        validate();
    }

    private void validate() {
        validateMemberId();
        validateTheme();
        validatePlayDate();
        validateCleared();
        validateClearTime();
        validateHintCount();
        validateRating();
        validateComment();
    }


    private void validateMemberId() {
        if (member == null) {
            throw new IllegalArgumentException("회원 정보가 누락되었습니다.");
        }
    }

    private void validateTheme() {
        if (theme == null) {
            throw new IllegalArgumentException("테마 정보가 누락되었습니다.");
        }
    }

    private void validatePlayDate() {
        if (playDate == null) {
            throw new IllegalArgumentException("플레이 날짜는 필수입니다.");
        }
        if (playDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("플레이 날짜는 미래일 수 없습니다.");
        }
    }

    private void validateCleared() {
        if (cleared == null) {
            throw new IllegalArgumentException("성공 여부는 필수 입니다.");
        }
    }

    private void validateClearTime() {
        if (cleared) {
            if (clearTimeSec <= 0) {
                throw new IllegalArgumentException("성공한 기록에는 유효한 클리어 시간이 필요합니다.");
            }
        }
        if (!cleared) {
            if (clearTimeSec != 0) {
                throw new IllegalArgumentException("실패한 기록에는 클리어 시간을 입력할 수 없습니다.");
            }
        }
    }

    private void validateHintCount() {
        if (hintCount < 0) {
            throw new IllegalArgumentException("힌트 사용 개수는 음수일 수 없습니다.");
        }
    }

    private void validateRating() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5점 사이여야 합니다.");
        }
    }

    private void validateComment() {
        if (comment != null && comment.length() > 500) {
            throw new IllegalArgumentException("후기는 500자 이하로 입력해주세요.");
        }
    }

    // 동일성 비교
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayRecord)) {
            return false;
        }
        PlayRecord that = (PlayRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
