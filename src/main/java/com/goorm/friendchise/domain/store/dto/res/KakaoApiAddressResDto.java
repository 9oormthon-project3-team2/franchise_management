package com.goorm.friendchise.domain.store.dto.res;

public record KakaoApiAddressResDto(
        String address,
        String roadAddress,
        String zoneNumber,
        String dong,
        Double x,
        Double y
) {


}
