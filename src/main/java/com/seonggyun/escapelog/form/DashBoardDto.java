package com.seonggyun.escapelog.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashBoardDto {
    private long totalPlays;
    private Integer successRate;
    private long storeCount;
    private String avgTime;
}
