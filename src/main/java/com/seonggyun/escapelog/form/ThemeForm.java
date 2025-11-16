package com.seonggyun.escapelog.form;

import com.seonggyun.escapelog.domain.Genre;
import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeForm {

    @NotBlank(message = "테마명을 입력해주세요.")
    private String title;

    @NotNull(message = "난이도를 입력해주세요.")
    @Min(value = 1, message = "난이도는 1 이상이어야 합니다.")
    @Max(value = 5, message = "난이도는 5 이하이어야 합니다.")
    private Integer difficulty;

    @NotNull(message = "제한 시간을 입력해주세요.")
    @Min(value = 0, message = "제한 시간은 0분 이상이어야 합니다.")
    @Max(value = 300, message = "제한 시간은 300분 이하이어야 합니다.")
    private Integer durationMin;

    @NotNull(message = "최소 인원을 입력해주세요.")
    @Min(value = 1, message = "최소 인원은 1명 이상이어야 합니다.")
    private Integer minPlayer;

    @NotNull(message = "최대 인원을 입력해주세요.")
    @Min(value = 1, message = "최대 인원은 1명 이상이어야 합니다.")
    private Integer maxPlayer;

    @NotNull(message = "매장을 선택해주세요.")
    private Long venueId;

    @NotEmpty(message = "하나 이상의 장르를 선택해주세요.")
    private Set<Genre> genres;
}
