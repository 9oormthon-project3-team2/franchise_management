package com.goorm.friendchise.domain.customer.application;

import com.goorm.friendchise.domain.customer.domain.Coordinates;

class FakeKaKaoApiService extends KaKaoApiService {
    public FakeKaKaoApiService() {
        super(null);
    }

    @Override
    public Coordinates getCoordinatesByAddress(String address) {
        // 테스트에서는 임의의 좌표값을 반환 (예: 서울 중심 좌표)
        return new Coordinates(37.5665, 126.9780);
    }
}