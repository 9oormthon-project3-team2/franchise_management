package com.goorm.friendchise.global.auth.infrastructure;

import com.goorm.friendchise.global.auth.domain.RefreshToken;
import com.goorm.friendchise.global.auth.domain.RefreshTokenRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {
	private final List<RefreshToken> data = Collections.synchronizedList(new ArrayList<>());

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		RefreshToken result = RefreshToken.of(
			refreshToken.getRefreshToken(), refreshToken.getId(), refreshToken.getRole()
		);
		data.add(result);
		return result;
	}

	@Override
	public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
		for (RefreshToken token : data) {
			if (token.getRefreshToken().equals(refreshToken)) {
				return Optional.of(token);
			}
		}
		return Optional.empty();
	}
}
