package com.goorm.friendchise.domain.customer.application;

import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.domain.customer.domain.CustomerRepository;
import com.goorm.friendchise.domain.customer.dto.request.CustomerCreateRequest;
import com.goorm.friendchise.domain.customer.dto.request.CustomerLoginRequest;
import com.goorm.friendchise.domain.customer.dto.response.CustomerDetailResponse;
import com.goorm.friendchise.domain.customer.dto.response.CustomerPersistResponse;
import com.goorm.friendchise.domain.customer.dto.response.CustomerTokenResponse;
import com.goorm.friendchise.domain.customer.exception.CustomerException;
import com.goorm.friendchise.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public CustomerPersistResponse create(CustomerCreateRequest request)
    {
        if(customerRepository.existsByUsername(request.username()))
        {
            throw new CustomerException(ErrorCode.DUPLICATE_LOGIN_ID);
        }
        Customer customer = customerRepository.save(
                Customer.builder().username(request.username()).password(bCryptPasswordEncoder.encode(request.password())).build());
        return CustomerPersistResponse.of(customer);
    }

    public CustomerTokenResponse login(CustomerLoginRequest request) {
        return CustomerTokenResponse.of("", "");
    }

    public CustomerDetailResponse detail(String username)
    {
        return CustomerDetailResponse.from(findCustomerByUsername(username));
    }

    @Transactional
    public void updatePassword(String username, String newPassword)
    {
        Customer customer=findCustomerByUsername(username);
        if (newPassword == null || newPassword.isBlank()||newPassword.contains(" ")||newPassword.length()>15||newPassword.length()<8) {
            throw new CustomerException(ErrorCode.INVALID_PASSWORD);
        }
        customer.updatePassword(bCryptPasswordEncoder.encode(newPassword));
    }

    private Customer findCustomerByUsername(String username)
    {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerException(ErrorCode.USER_NOT_FOUND));
    }
}
