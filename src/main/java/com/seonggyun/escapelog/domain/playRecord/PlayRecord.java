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

    private static final int RATING_MIN = 1;
    private static final int RATING_MAX = 5;

    private static final int COMMENT_MAX_LENGTH = 500;

    private static final String ERROR_MEMBER_REQUIRED = "회원 정보가 누락되었습니다.";
    private static final String ERROR_THEME_REQUIRED = "테마 정보가 누락되었습니다.";
    private static final String ERROR_PLAY_DATE_REQUIRED = "플레이 날짜는 필수입니다.";
    private static final String ERROR_PLAY_DATE_FUTURE = "플레이 날짜는 미래일 수 없습니다.";
    private static final String ERROR_CLEARED_REQUIRED = "성공 여부는 필수입니다.";
    private static final String ERROR_CLEAR_TIME_REQUIRED = "성공한 기록에는 유효한 클리어 시간이 필요합니다.";
    private static final String ERROR_CLEAR_TIME_FORBIDDEN = "실패한 기록에는 클리어 시간을 입력할 수 없습니다.";
    private static final String ERROR_HINT_NEGATIVE = "힌트 사용 개수는 음수일 수 없습니다.";

    private static final String ERROR_RATING_RANGE_TEMPLATE = "평점은 %d~%d점 사이여야 합니다.";
    private static final String ERROR_COMMENT_LENGTH_TEMPLATE = "후기는 %d자 이하로 입력해주세요.";

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

    public PlayRecord(Member member, Theme theme, LocalDate playDate, Boolean cleared,
                      int clearTimeSec, int hintCount, int rating, String comment) {

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
        validateMember();
        validateTheme();
        validatePlayDate();
        validateCleared();
        validateClearTime();
        validateHintCount();
        validateRating();
        validateComment();
    }

    private void validateMember() {
        if (member == null) {
            throw new IllegalArgumentException(ERROR_MEMBER_REQUIRED);
        }
    }

    private void validateTheme() {
        if (theme == null) {
            throw new IllegalArgumentException(ERROR_THEME_REQUIRED);
        }
    }

    private void validatePlayDate() {
        if (playDate == null) {
            throw new IllegalArgumentException(ERROR_PLAY_DATE_REQUIRED);
        }
        if (playDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ERROR_PLAY_DATE_FUTURE);
        }
    }

    private void validateCleared() {
        if (cleared == null) {
            throw new IllegalArgumentException(ERROR_CLEARED_REQUIRED);
        }
    }

    private void validateClearTime() {
        if (cleared) {
            if (clearTimeSec <= 0) {
                throw new IllegalArgumentException(ERROR_CLEAR_TIME_REQUIRED);
            }
        } else {
            if (clearTimeSec != 0) {
                throw new IllegalArgumentException(ERROR_CLEAR_TIME_FORBIDDEN);
            }
        }
    }

    private void validateHintCount() {
        if (hintCount < 0) {
            throw new IllegalArgumentException(ERROR_HINT_NEGATIVE);
        }
    }

    private void validateRating() {
        if (rating < RATING_MIN || rating > RATING_MAX) {
            throw new IllegalArgumentException(
                    String.format(ERROR_RATING_RANGE_TEMPLATE, RATING_MIN, RATING_MAX)
            );
        }
    }

    private void validateComment() {
        if (comment != null && comment.length() > COMMENT_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format(ERROR_COMMENT_LENGTH_TEMPLATE, COMMENT_MAX_LENGTH)
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayRecord)) return false;
        PlayRecord that = (PlayRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
