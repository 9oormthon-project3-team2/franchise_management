package com.goorm.friendchise.domain.headquarter.Item.dto;

public record ItemResDto(
        Long id,
        String name,
        int price
) {
    public static ItemResDto of(Long id, String name, int price) {
        return new ItemResDto(id, name, price);
    }
}
