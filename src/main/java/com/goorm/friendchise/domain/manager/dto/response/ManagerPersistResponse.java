package com.goorm.friendchise.domain.manager.dto.response;

import lombok.Builder;

@Builder
public record ManagerPersistResponse(
	Long id
) {
	public static ManagerPersistResponse of(Long id) {
		return ManagerPersistResponse.builder().id(id).build();
	}
}
