package com.goorm.friendchise.domain.customer.dto.response;

import lombok.Builder;

@Builder
public record CustomerTokenResponse(String accessToken,
                                    String refreshToken
) {
    public static CustomerTokenResponse of(String accessToken, String refreshToken) {
        return CustomerTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
