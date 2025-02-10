package com.goorm.friendchise.domain.customer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Achievement {
    GANGDONG_MASTER("강동구 마스터"),
    GANGNAM_MASTER("강남구 마스터"),
    WALKING_NOOP("걷기 초보"),
    WALKING_KING("걷기 왕");

    private final String description;

}
