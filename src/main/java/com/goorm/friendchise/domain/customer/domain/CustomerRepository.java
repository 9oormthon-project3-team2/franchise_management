package com.goorm.friendchise.domain.customer.domain;

import com.goorm.friendchise.domain.manager.domain.Manager;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByUsername(String username);

    void delete(Customer customer);
}
