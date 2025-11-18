package com.seonggyun.escapelog.domain.playRecord;

import com.seonggyun.escapelog.domain.playRecord.exception.PlayRecordErrorCode;
import com.seonggyun.escapelog.domain.playRecord.exception.PlayRecordException;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.member.Member;
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
            throw new PlayRecordException(PlayRecordErrorCode.MEMBER_REQUIRED);
        }
    }

    private void validateTheme() {
        if (theme == null) {
            throw new PlayRecordException(PlayRecordErrorCode.THEME_REQUIRED);
        }
    }

    private void validatePlayDate() {
        if (playDate == null) {
            throw new PlayRecordException(PlayRecordErrorCode.PLAY_DATE_REQUIRED);
        }
        if (playDate.isAfter(LocalDate.now())) {
            throw new PlayRecordException(PlayRecordErrorCode.PLAY_DATE_IN_FUTURE);
        }
    }

    private void validateCleared() {
        if (cleared == null) {
            throw new PlayRecordException(PlayRecordErrorCode.CLEARED_REQUIRED);
        }
    }

    private void validateClearTime() {
        if (cleared) {
            if (clearTimeSec <= 0) {
                throw new PlayRecordException(PlayRecordErrorCode.CLEAR_TIME_REQUIRED);
            }
        } else {
            if (clearTimeSec != 0) {
                throw new PlayRecordException(PlayRecordErrorCode.CLEAR_TIME_FORBIDDEN);
            }
        }
    }

    private void validateHintCount() {
        if (hintCount < 0) {
            throw new PlayRecordException(PlayRecordErrorCode.HINT_COUNT_NEGATIVE);
        }
    }

    private void validateRating() {
        if (rating < RATING_MIN || rating > RATING_MAX) {
            throw new PlayRecordException(PlayRecordErrorCode.RATING_RANGE_INVALID);
        }
    }

    private void validateComment() {
        if (comment != null && comment.length() > COMMENT_MAX_LENGTH) {
            throw new PlayRecordException(PlayRecordErrorCode.COMMENT_LENGTH_INVALID);
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
