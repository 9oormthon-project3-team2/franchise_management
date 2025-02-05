package com.goorm.friendchise.manager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	HEADQUARTER("본사"),
	STORE("매장");

	private final String description;
}
