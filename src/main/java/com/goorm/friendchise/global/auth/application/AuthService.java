package com.goorm.friendchise.global.auth.application;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.ManagerRepository;
import com.goorm.friendchise.domain.manager.exception.ManagerNotFoundException;
import com.goorm.friendchise.domain.manager.exception.TokenNotFoundException;
import com.goorm.friendchise.global.auth.domain.RefreshTokenRepository;
import com.goorm.friendchise.global.auth.dto.request.TokenReissueRequest;
import com.goorm.friendchise.global.auth.dto.response.AccessTokenResponse;
import com.goorm.friendchise.global.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
	private final ManagerRepository managerRepository;
	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	private static final Duration ACCESS_TOKEN_EXP = Duration.ofHours(1);

	public Manager findManagerByAuth() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = ((UserDetails) principal).getUsername();
			return managerRepository.findByUsername(username)
				.orElseThrow(ManagerNotFoundException::new);
		} catch (Exception e) {
			throw new ManagerNotFoundException();
		}
	}


	public AccessTokenResponse reissue(TokenReissueRequest request) {
		String inputRefreshToken = request.refreshToken();

		String username = tokenProvider.getUsername(inputRefreshToken);
		String role = refreshTokenRepository.findByRefreshToken(inputRefreshToken)
			.orElseThrow(TokenNotFoundException::new)
			.getRole()
			.name();

		String accessToken = tokenProvider.generateToken(username, ACCESS_TOKEN_EXP, role);
		return AccessTokenResponse.of(accessToken);
	}
}
