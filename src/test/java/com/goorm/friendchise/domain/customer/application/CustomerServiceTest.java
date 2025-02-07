package com.goorm.friendchise.domain.customer.application;

import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.domain.customer.domain.CustomerRepository;
import com.goorm.friendchise.domain.customer.dto.request.CustomerCreateRequest;
import com.goorm.friendchise.domain.customer.dto.response.CustomerDetailResponse;
import com.goorm.friendchise.domain.customer.dto.response.CustomerPersistResponse;
import com.goorm.friendchise.domain.customer.exception.CustomerException;
import com.goorm.friendchise.domain.customer.infrastructure.FakeCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerServiceTest {
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new FakeCustomerRepository();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customerService = new CustomerService(customerRepository, bCryptPasswordEncoder);

        customerRepository.save(
                Customer.builder().username("testUser").password("testPassword").build()
        );
    }

    @Test
    void 생성_성공(){
        CustomerCreateRequest request
                = CustomerCreateRequest.builder().username("testUser2").password("testPassword").build();
        CustomerPersistResponse response= customerService.create(request);
        assertEquals(2L,response.id());
    }
    @Test
    void 생성_실패_중복아이디(){
        CustomerCreateRequest request
                = CustomerCreateRequest.builder().username("testUser").password("testPassword").build();
        assertThrows(CustomerException.class, () -> customerService.create(request));
    }

    @Test
    void 유저가져오기(){
        CustomerDetailResponse detail = customerService.detail("testUser");
        assertEquals("testUser", detail.username());
        assertEquals(1L, detail.id());
    }


}
