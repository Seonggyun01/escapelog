package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayRecordForm {
    private static final int CLEAR_MINUTES_MIN = 0;
    private static final int CLEAR_MINUTES_MAX = 300;

    private static final int CLEAR_SECONDS_MIN = 0;
    private static final int CLEAR_SECONDS_MAX = 59;

    private static final int HINT_MIN = 0;

    private static final int RATING_MIN = 1;
    private static final int RATING_MAX = 5;

    private static final int COMMENT_MAX_LENGTH = 500;

    private static final String ERROR_THEME_REQUIRED = "테마를 선택해주세요.";
    private static final String ERROR_DATE_REQUIRED = "날짜를 선택해주세요.";
    private static final String ERROR_DATE_FUTURE = "미래는 선택할 수 없습니다.";

    private static final String ERROR_CLEARED_REQUIRED = "성공 여부를 입력해주세요.";

    private static final String ERROR_MINUTES_MIN = "분은 0 이상이어야 합니다.";
    private static final String ERROR_MINUTES_MAX = "분은 300분 이하만 입력 가능합니다.";

    private static final String ERROR_SECONDS_MIN = "초는 0 이상이어야 합니다.";
    private static final String ERROR_SECONDS_MAX = "초는 59초 이하만 입력 가능합니다.";

    private static final String ERROR_HINT_MIN = "힌트 사용 개수는 0 이상이어야 합니다.";

    private static final String ERROR_RATING_MIN = "별점은 1점 이상입니다.";
    private static final String ERROR_RATING_MAX = "별점은 5점 이하여야 합니다.";

    private static final String ERROR_COMMENT_MAX = "500자까지 입력 가능합니다.";

    @NotNull(message = ERROR_THEME_REQUIRED)
    private Long themeId;

    @NotNull(message = ERROR_DATE_REQUIRED)
    @PastOrPresent(message = ERROR_DATE_FUTURE)
    private LocalDate playDate;

    @NotNull(message = ERROR_CLEARED_REQUIRED)
    private Boolean cleared;

    @Min(value = CLEAR_MINUTES_MIN, message = ERROR_MINUTES_MIN)
    @Max(value = CLEAR_MINUTES_MAX, message = ERROR_MINUTES_MAX)
    private Integer clearMinutes;

    @Min(value = CLEAR_SECONDS_MIN, message = ERROR_SECONDS_MIN)
    @Max(value = CLEAR_SECONDS_MAX, message = ERROR_SECONDS_MAX)
    private Integer clearSeconds;

    @Min(value = HINT_MIN, message = ERROR_HINT_MIN)
    private int hintCount;

    @Min(value = RATING_MIN, message = ERROR_RATING_MIN)
    @Max(value = RATING_MAX, message = ERROR_RATING_MAX)
    private int rating;

    @Size(max = COMMENT_MAX_LENGTH, message = ERROR_COMMENT_MAX)
    private String comment;

    public int getClearTimeSecAsInt() {
        if (!Boolean.TRUE.equals(cleared)) {
            return 0;  // 실패한 경우 0초로 고정
        }
        int minutes = clearMinutes != null ? clearMinutes : 0;
        int seconds = clearSeconds != null ? clearSeconds : 0;
        return minutes * 60 + seconds;
    }
}
