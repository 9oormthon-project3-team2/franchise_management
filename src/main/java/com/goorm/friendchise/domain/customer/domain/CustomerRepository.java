package com.goorm.friendchise.domain.customer.domain;

import com.goorm.friendchise.domain.manager.domain.Manager;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByUsername(String username);

    boolean existsByUsername(String username);

    List<Customer> findAll();

    void deleteAll();

    void delete(Customer customer);
}
