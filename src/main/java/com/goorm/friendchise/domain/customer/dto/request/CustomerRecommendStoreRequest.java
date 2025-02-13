package com.goorm.friendchise.domain.customer.dto.request;

import jakarta.validation.constraints.NotNull;

public record CustomerRecommendStoreRequest(
        @NotNull
        String address,
        @NotNull
        String franchiseName
)
{

}
