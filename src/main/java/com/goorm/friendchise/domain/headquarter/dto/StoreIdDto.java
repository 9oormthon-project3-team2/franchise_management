package com.goorm.friendchise.domain.headquarter.dto;

public record StoreIdDto(
        Long id
) {
    public static StoreIdDto of(Long id) {
        return new StoreIdDto(id);
    }
}
