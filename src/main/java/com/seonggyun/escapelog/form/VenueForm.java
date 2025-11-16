package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VenueForm {
    @NotBlank(message = "매장명을 입력해주세요.")
    private String name;

    @NotBlank(message = "지역을 입력해주세요.")
    private String region;
}
