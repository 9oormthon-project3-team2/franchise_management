package com.goorm.friendchise.domain.customer.dto.response;


import com.goorm.friendchise.domain.customer.domain.Customer;
import lombok.Builder;

@Builder
public record CustomerPersistResponse(Long id
) {
    public static CustomerPersistResponse of(Customer customer) {
        return CustomerPersistResponse.builder().id(customer.getId()).build();
    }
}
