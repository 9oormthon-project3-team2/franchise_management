package com.goorm.friendchise.domain.customer.dto.response;

import com.goorm.friendchise.domain.customer.domain.Achievement;
import com.goorm.friendchise.domain.customer.domain.Customer;
import lombok.Builder;
import java.util.Set;

@Builder
public record CustomerDetailResponse(
        Long id,
        String username,
        Double movedDistance
) {
    public static CustomerDetailResponse from(Customer customer)
    {
        return CustomerDetailResponse.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .movedDistance(customer.getMovedDistance())
                .build();
    }
}
