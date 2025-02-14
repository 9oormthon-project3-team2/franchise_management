package com.goorm.friendchise.domain.manager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	HEADQUARTER("본사"),
	STORE("매장"),
	USER("USER");
	private final String description;
}
