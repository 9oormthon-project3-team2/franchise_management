package com.goorm.friendchise.domain.customer.infrastructure;


import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.domain.customer.domain.CustomerRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;



public class FakeCustomerRepository implements CustomerRepository {

    private final Map<Long, Customer> store = new HashMap<>(); // In-memory 저장소
    private final AtomicLong sequence = new AtomicLong(1L); // ID 자동 증가

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) { // 새로운 고객이면 ID 부여
            customer = Customer.builder()
                    .id(sequence.getAndIncrement())
                    .username(customer.getUsername())
                    .password(customer.getPassword())
                    .achievements(customer.getAchievements())
                    .build();
        }
        store.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return store.values().stream()
                .filter(customer -> customer.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return store.values().stream()
                .anyMatch(customer -> customer.getUsername().equals(username));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Customer customer) {
        store.remove(customer.getId());
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
