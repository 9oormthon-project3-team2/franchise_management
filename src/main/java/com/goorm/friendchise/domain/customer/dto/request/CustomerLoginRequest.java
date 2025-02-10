package com.goorm.friendchise.domain.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerLoginRequest(
        @NotBlank()
        @Size(max = 50)
        String username,

        @NotBlank()
        @Size(min = 8, max = 15)
        String password)
{
}
