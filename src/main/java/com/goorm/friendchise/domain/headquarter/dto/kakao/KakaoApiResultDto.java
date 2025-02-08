package com.goorm.friendchise.domain.headquarter.dto.kakao;

import java.util.List;

public record KakaoApiResultDto(
        List<KakaoPlaceDto> documents
) {
}
