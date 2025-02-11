package com.goorm.friendchise.global.auth.domain;

import com.goorm.friendchise.domain.manager.domain.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24)
public class RefreshToken {
	@Indexed
	private String refreshToken;

	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	public static RefreshToken of(String refreshToken, Long id, Role role) {
		return RefreshToken.builder()
			.refreshToken(refreshToken)
			.id(id)
			.role(role)
			.build();
	}
}
