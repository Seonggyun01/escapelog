package com.seonggyun.escapelog.form;

import com.seonggyun.escapelog.domain.Genre;
import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeForm {
    private static final int TITLE_MAX_LENGTH = 120;
    private static final int DIFFICULTY_MIN = 1;
    private static final int DIFFICULTY_MAX = 5;
    private static final int DURATION_MIN = 0;
    private static final int DURATION_MAX = 300;
    private static final int PLAYER_MIN = 1;

    private static final String ERROR_TITLE_REQUIRED = "테마명을 입력해주세요.";
    private static final String ERROR_TITLE_LENGTH = "테마명은 120자 이하로 입력해주세요.";

    private static final String ERROR_DIFFICULTY_REQUIRED = "난이도를 입력해주세요.";
    private static final String ERROR_DIFFICULTY_MIN = "난이도는 1 이상이어야 합니다.";
    private static final String ERROR_DIFFICULTY_MAX = "난이도는 5 이하여야 합니다.";

    private static final String ERROR_DURATION_REQUIRED = "제한 시간을 입력해주세요.";
    private static final String ERROR_DURATION_MIN = "제한 시간은 0분 이상이어야 합니다.";
    private static final String ERROR_DURATION_MAX = "제한 시간은 300분 이하여야 합니다.";

    private static final String ERROR_MIN_PLAYER_REQUIRED = "최소 인원을 입력해주세요.";
    private static final String ERROR_MIN_PLAYER_MIN = "최소 인원은 1명 이상이어야 합니다.";

    private static final String ERROR_MAX_PLAYER_REQUIRED = "최대 인원을 입력해주세요.";
    private static final String ERROR_MAX_PLAYER_MIN = "최대 인원은 1명 이상이어야 합니다.";

    private static final String ERROR_VENUE_REQUIRED = "매장을 선택해주세요.";
    private static final String ERROR_GENRE_REQUIRED = "하나 이상의 장르를 선택해주세요.";

    @NotBlank(message = ERROR_TITLE_REQUIRED)
    @Size(max = TITLE_MAX_LENGTH, message = ERROR_TITLE_LENGTH)
    private String title;

    @NotNull(message = ERROR_DIFFICULTY_REQUIRED)
    @Min(value = DIFFICULTY_MIN, message = ERROR_DIFFICULTY_MIN)
    @Max(value = DIFFICULTY_MAX, message = ERROR_DIFFICULTY_MAX)
    private Integer difficulty;

    @NotNull(message = ERROR_DURATION_REQUIRED)
    @Min(value = DURATION_MIN, message = ERROR_DURATION_MIN)
    @Max(value = DURATION_MAX, message = ERROR_DURATION_MAX)
    private Integer durationMin;

    @NotNull(message = ERROR_MIN_PLAYER_REQUIRED)
    @Min(value = PLAYER_MIN, message = ERROR_MIN_PLAYER_MIN)
    private Integer minPlayer;

    @NotNull(message = ERROR_MAX_PLAYER_REQUIRED)
    @Min(value = PLAYER_MIN, message = ERROR_MAX_PLAYER_MIN)
    private Integer maxPlayer;

    @NotNull(message = ERROR_VENUE_REQUIRED)
    private Long venueId;

    @NotEmpty(message = ERROR_GENRE_REQUIRED)
    private Set<Genre> genres;
}
