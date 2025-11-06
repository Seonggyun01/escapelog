package com.seonggyun.escapelog.domain;

import lombok.Getter;

@Getter
public enum Genre {
    MYSTERY("추리"),
    HORROR("공포"),
    THRILLER("스릴러"),
    CRIME("범죄"),
    FANTASY("판타지"),
    SCIFI("공상과학"),
    ADVENTURE("모험"),
    HISTORICAL("시대극"),
    DETECTIVE("탐정"),
    STEALTH("잠입"),
    SURVIVAL("생존"),
    ESCAPE("탈출"),
    LOGIC("논리"),
    PUZZLE("퍼즐"),
    DRAMA("드라마"),
    COMEDY("코믹"),
    MELO("감성");

    private final String koreanName;

    Genre(String koreanName){
        this.koreanName = koreanName;
    }

    @Override
    public String toString() {
        return koreanName;
    }
}
