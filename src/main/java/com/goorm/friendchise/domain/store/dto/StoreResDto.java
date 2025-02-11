package com.goorm.friendchise.domain.store.dto;

import com.goorm.friendchise.domain.store.domain.Store;

public record StoreResDto(
        Long id,
        String address,
        String dong,
        Double pointX,
        Double pointY,
        String franchiseName
) {
    public StoreResDto(Store store) {
        this(store.getId(), store.getAddress(), store.getDong(), store.getPointX(), store.getPointY(), store.getFranchiseName());
    }
}
