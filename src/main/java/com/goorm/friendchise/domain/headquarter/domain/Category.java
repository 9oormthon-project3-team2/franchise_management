package com.goorm.friendchise.domain.headquarter.domain;

import lombok.Getter;

@Getter
public enum Category {
    FASTFOOD("패스트푸드"),
    SNACKFOOD("분식"),
    KOREANFOOD("한식"),
    CHINESEFOOD("중식"),
    JAPANESEFOOD("일식"),
    WESTERNFOOD("양식"),
    CAFE("카페"),
    DESSERT("디저트"),
    SNACK("간식"),
    BAR("술집")
    ;

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
