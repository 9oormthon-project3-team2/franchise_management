package com.goorm.friendchise.global.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {
	RefreshToken save(RefreshToken refreshToken);

	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
