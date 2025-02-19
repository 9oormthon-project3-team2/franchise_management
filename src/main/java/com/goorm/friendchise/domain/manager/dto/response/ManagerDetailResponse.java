package com.goorm.friendchise.domain.manager.dto.response;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.Role;
import lombok.Builder;

@Builder
public record ManagerDetailResponse(
	Long id,
	String username,
	Role role,
	Long manageId,
	String certificationNumber
) {
	public static ManagerDetailResponse from(Manager manager) {
		return ManagerDetailResponse.builder()
			.id(manager.getId())
			.username(manager.getUsername())
			.role(manager.getRole())
			.manageId(manager.getManageId())
			.build();
	}

	public static ManagerDetailResponse fromHeadquarter(Manager manager, String headquarterCertificationNumber) {
		return ManagerDetailResponse.builder()
			.id(manager.getId())
			.username(manager.getUsername())
			.role(manager.getRole())
			.manageId(manager.getManageId())
			.certificationNumber(headquarterCertificationNumber)
			.build();
	}
}
