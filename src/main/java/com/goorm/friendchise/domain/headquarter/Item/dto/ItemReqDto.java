package com.goorm.friendchise.domain.headquarter.Item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ItemReqDto(
        @NotBlank @Size(min = 1, max = 50) String name,
        @PositiveOrZero @Max(Integer.MAX_VALUE) int price
        ) {
}
