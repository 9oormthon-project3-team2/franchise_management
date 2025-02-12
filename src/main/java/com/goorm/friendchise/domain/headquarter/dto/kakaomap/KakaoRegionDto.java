package com.goorm.friendchise.domain.headquarter.dto.kakaomap;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoRegionDto(
        String regionType,
        @JsonProperty("region_2depth_name")
        String guName,
        @JsonProperty("region_3depth_name")
        String hDongName
) {
}
