package com.goorm.friendchise.global.auth.dto.response;

import lombok.Builder;

@Builder
public record AccessTokenResponse(
	String accessToken
) {
	public static AccessTokenResponse of(String accessToken) {
		return AccessTokenResponse.builder()
			.accessToken(accessToken)
			.build();
	}
}
