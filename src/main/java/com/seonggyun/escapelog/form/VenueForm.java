package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VenueForm {
    private static final int VENUE_NAME_MAX_LENGTH = 100;
    private static final int REGION_MAX_LENGTH = 60;

    private static final String ERROR_NAME_REQUIRED = "매장명을 입력해주세요.";
    private static final String ERROR_REGION_REQUIRED = "지역을 입력해주세요.";

    private static final String ERROR_NAME_LENGTH = "매장명은 100자 이하로 입력해주세요.";
    private static final String ERROR_REGION_LENGTH = "지역은 60자 이하로 입력해주세요.";

    @NotBlank(message = ERROR_NAME_REQUIRED)
    @Size(max = VENUE_NAME_MAX_LENGTH, message = ERROR_NAME_LENGTH)
    private String name;

    @NotBlank(message = ERROR_REGION_REQUIRED)
    @Size(max = REGION_MAX_LENGTH, message = ERROR_REGION_LENGTH)
    private String region;
}
