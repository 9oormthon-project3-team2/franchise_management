package com.goorm.friendchise.domain.store.dto.res;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record StoreRegisterDto(
	@NotBlank(message = "주소는 필수 입력 값입니다.")
	String address,

	@NotBlank(message = "동 정보는 필수 입력 값입니다.")
	String dong,

	@NotNull(message = "X 좌표는 필수 입력 값입니다.")
	Double pointX,

	@NotNull(message = "Y 좌표는 필수 입력 값입니다.")
	Double pointY,

	@NotBlank(message = "프랜차이즈 이름은 필수 입력 값입니다.")
	String franchiseName
) {
	public static StoreRegisterDto of(String address, String dong, Double pointX, Double pointY, String franchiseName) {
		return new StoreRegisterDto(address, dong, pointX, pointY, franchiseName);
	}
}
