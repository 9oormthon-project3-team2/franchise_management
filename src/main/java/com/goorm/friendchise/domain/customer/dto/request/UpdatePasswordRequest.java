package com.goorm.friendchise.domain.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 15, message = "비밀번호는 8~15자로 입력해야 합니다.")
        String newPassword
) {
}
