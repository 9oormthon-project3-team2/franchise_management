package com.goorm.friendchise.manager.dto.request;

import com.goorm.friendchise.manager.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record ManageCreateRequest(
	@Schema(description = "로그인아이디", example = "organking", requiredMode = REQUIRED)
	@NotNull
	String username,

	@Schema(description = "비밀번호", example = "organking1234", requiredMode = REQUIRED)
	@NotNull
	String password,

	@Schema(description = "역할", example = "HEADQUARTER", requiredMode = REQUIRED)
	@NotNull
	Role role
) {
}
