package com.goorm.friendchise.domain.customer.application;

import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.domain.customer.domain.CustomerRepository;
import com.goorm.friendchise.domain.customer.dto.request.CustomerCreateRequest;
import com.goorm.friendchise.domain.customer.dto.request.CustomerRecommendStoreRequest;
import com.goorm.friendchise.domain.customer.dto.response.CustomerDetailResponse;
import com.goorm.friendchise.domain.customer.dto.response.CustomerPersistResponse;
import com.goorm.friendchise.domain.customer.exception.CustomerException;
import com.goorm.friendchise.domain.customer.infrastructure.FakeCustomerRepository;
import com.goorm.friendchise.domain.customer.infrastructure.FakeStoreRepository;
import com.goorm.friendchise.domain.redis.config.RedisConfigTest;
import com.goorm.friendchise.global.config.WebClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@DataRedisTest // Redis 관련 설정만 로드
@Import({RedisConfigTest.class,WebClientConfig.class, KaKaoApiService.class})
// 테스트용 Redis 설정만 Import
public class CustomerServiceTest {
    private CustomerService customerService;
    private Customer customer;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KaKaoApiService kaKaoApiService;
    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new FakeCustomerRepository();
        FakeStoreRepository fakeStoreRepository = new FakeStoreRepository();
        FakeKaKaoApiService fakeKaKaoApiService = new FakeKaKaoApiService();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        CustomerCreateRequest request=new CustomerCreateRequest("testUser","testPassword");
        customerService = new CustomerService(customerRepository, bCryptPasswordEncoder,fakeStoreRepository,kaKaoApiService,redisTemplate);
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

    @Test
    void testConcurrentFindNearestStoreWithCache() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<String>> futures = new ArrayList<>();
        CustomerRecommendStoreRequest request=new CustomerRecommendStoreRequest("경기도 하남시 미사강변동로 20","맥도날드");
        // 스레드 10개 동시에 실행
        for (int i = 0; i < threadCount; i++) {
            Future<String> future = executorService.submit(() ->
                    customerService.findNearestStoreWithCache(request)
            );
            futures.add(future);
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // 스레드 실행 완료 후 결과 확인
        for (Future<String> future : futures) {
            try {
                String store = future.get();
                System.out.println("매장 반환됨: " + store);
            } catch (Exception e) {
                System.err.println("오류 발생: " + e.getMessage());
            }
        }

    }
}
