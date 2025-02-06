package com.goorm.friendchise.domain.customer.infrastructure;

import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.domain.customer.domain.CustomerRepository;
import com.goorm.friendchise.domain.manager.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return jpaCustomerRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username){ return jpaCustomerRepository.existsByUsername(username);}

    @Override
    public void delete(Customer customer) {
        jpaCustomerRepository.delete(customer);
    }
}