package com.goorm.friendchise.domain.manager.dto.response;

import lombok.Builder;

@Builder
public record ManagerTokenResponse(
	String accessToken,
	String refreshToken
) {
	public static ManagerTokenResponse of(String accessToken, String refreshToken) {
		return ManagerTokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
