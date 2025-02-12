package com.goorm.friendchise.global.auth.infrastructure;

import com.goorm.friendchise.global.auth.domain.RefreshToken;
import com.goorm.friendchise.global.auth.domain.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
	private final RedisRefreshTokenRepository redisRefreshTokenRepository;

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		return redisRefreshTokenRepository.save(refreshToken);
	}

	@Override
	public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
		return redisRefreshTokenRepository.findByRefreshToken(refreshToken);
	}
}
