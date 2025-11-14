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
    MELO("감성"),
    HISTORY("역사"),
    MYSTERY_KR("미스테리"),
    GAG("개그"),
    ARCADE("아케이드"),
    INVESTIGATION("수사"),
    ACTION("액션"),
    COOP("협동"),
    EXPLORATION("탐험"),
    HIPHOP("힙합"),
    LOVE("연애"),
    ROMANCE("로맨스"),
    DARK_FAIRYTALE("잔혹동화"),
    SLICE_OF_LIFE("일상"),
    COMEDY_KR("코미디"),
    TOUCHING("감동"),
    ADVENTURE_KR("어드벤처");

    private final String koreanName;

    Genre(String koreanName){
        this.koreanName = koreanName;
    }

    @Override
    public String toString() {
        return koreanName;
    }
}
