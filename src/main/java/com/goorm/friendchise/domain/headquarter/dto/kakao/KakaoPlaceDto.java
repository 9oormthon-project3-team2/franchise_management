package com.goorm.friendchise.domain.headquarter.dto.kakao;

public record KakaoPlaceDto(
        String placeName,
        String distance,
        String categoryName,
        String categoryGroupCode,
        String categoryGroupName,
        String x,
        String y
) {
}
