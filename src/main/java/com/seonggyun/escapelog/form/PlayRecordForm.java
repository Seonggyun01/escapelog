package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayRecordForm {

    @NotNull(message = "테마를 선택해주세요.")
    private Long themeId;

    @NotNull(message = "날짜를 선택해주세요.")
    @PastOrPresent(message = "미래는 선택할 수 없습니다.")
    private LocalDate playDate;

    @NotNull(message = "성공 여부를 입력해주세요.")
    private Boolean cleared;

    @Min(value = 0, message = "분은 0 이상이어야 합니다.")
    @Max(value = 300, message = "분은 300분 이하만 입력 가능합니다.")
    private Integer clearMinutes;

    @Min(value = 0, message = "초은 0 이상이어야 합니다.")
    @Max(value = 59, message = "초은 59초 이하만 입력 가능합니다.")
    private Integer clearSeconds;

    @Min(value = 0, message = "힌트 사용 개수를 입력해주세요.")
    private int hintCount;

    @Min(value = 1, message = "별점은 1점 이상입니다.")
    @Max(value = 5, message = "별점은 5점 이하입니다,")
    private int rating;

    @Size(max = 500, message = "500자까지 입력 가능합니다.")
    private String comment;

    public int getClearTimeSecAsInt() {
        if (!Boolean.TRUE.equals(cleared)) {
            return 0;  // 실패이면 항상 0초로 저장
        }
        int minutes = clearMinutes != null ? clearMinutes : 0;
        int seconds = clearSeconds != null ? clearSeconds : 0;
        return minutes * 60 + seconds;
    }
}
