package com.goorm.friendchise.domain.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerCreateRequest(
        @NotBlank(message = "사용자 이름은 필수입니다.")
        @Size(max = 50, message = "사용자 이름은 최대 50자까지 입력 가능합니다.")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 최대 15자 이하로 입력해야 합니다.")
        String password)
{

}
