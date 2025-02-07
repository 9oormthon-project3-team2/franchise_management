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

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    private Customer customer;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new FakeCustomerRepository();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        CustomerCreateRequest request=new CustomerCreateRequest("testUser","testPassword");
        customerService = new CustomerService(customerRepository, bCryptPasswordEncoder);
        customerService.create(request);
        customer=customerRepository.findByUsername("testUser").orElseThrow();

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

    @Test
    void 비밀번호_업데이트_성공_(){

        customerService.updatePassword("testUser","newPassword");
        assertTrue(bCryptPasswordEncoder.matches("newPassword", customer.getPassword()));

    }

    @Test
    void 비밀번호_업데이트_실패_null(){
        assertThrows(CustomerException.class, () -> customerService.updatePassword("testUser",null));

    }

    @Test
    void 비밀번호_업데이트_실패_Blank_값(){
        assertThrows(CustomerException.class, () -> customerService.updatePassword("testUser",""));

    }
    @Test
    void 비밀번호_업데이트_실패_공백이있음(){
        assertThrows(CustomerException.class, () -> customerService.updatePassword("testUser","new Password"));

    }
    @Test
    void 비밀번호_업데이트_실패_길이제한(){
        assertThrows(CustomerException.class, () -> customerService.updatePassword("testUser","nesdsdsddsPassword"));
        assertThrows(CustomerException.class, () -> customerService.updatePassword("testUser","123"));


    }
}
