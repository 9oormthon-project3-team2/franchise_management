package com.goorm.friendchise.domain.customer.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.friendchise.FriendchiseApplication;
import com.goorm.friendchise.domain.customer.dto.request.CustomerRecommendStoreRequest;
import com.goorm.friendchise.domain.redis.config.RedisConfigTest;
import com.goorm.friendchise.domain.store.application.StoreService;
import com.goorm.friendchise.domain.store.infrastructure.StoreRepository;
import com.goorm.friendchise.global.config.RedisConfig;
import com.goorm.friendchise.global.config.WebClientConfig;
import com.goorm.friendchise.global.redis.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
@SpringBootTest(classes = FriendchiseApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
public class apiTest {
    private CustomerService customerService;
    private CustomerTestService customerTestService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisServiceRedisTemplate;
    @Autowired
    private KaKaoApiService kaKaoApiService;
    @Autowired
    private StoreRepository storeRepository;



    private final List<String> uniqueAddresses = List.of(
            "경기 하남시 미사강변남로 39",
            "경기 하남시 미사강변남로 10",
            "경기 하남시 미사강변서로 127",
            "경기 하남시 미사강변동로 47",
            "경기 하남시 미사강변중앙로 120",
            "경기 하남시 미사강변동로 95",
            "경기 하남시 미사강변한강로 270",
            "경기 하남시 미사강변대로 165",
            "경기 하남시 미사강변동로 19",
            "경기 하남시 덕풍북로 100",
            "경기 하남시 미사강변대로 135",
            "경기 하남시 미사강변남로 35",
                "경기도 하남시 감북로 39",
          "경기도 하남시 검단남로 48",
            "경기도 하남시 서하남로 490",
            "경기도 하남시 위례순환로 310",
            "경기도 하남시 초이로 65",
            "경기도 하남시 덕풍서로 66",
            "경기도 하남시 아리수로 531"

    );
    @BeforeEach
    void setUp()
    {
        RedisService redisService = new RedisService(redisServiceRedisTemplate);
        StoreService storeService = new StoreService(storeRepository, null,
                null, null, null, redisServiceRedisTemplate, new ObjectMapper());

        customerTestService=new CustomerTestService(kaKaoApiService,redisTemplate, storeService);

        customerService = new CustomerService(null, null,
                kaKaoApiService,redisTemplate,null,null,redisService, storeService);
        storeService.fetchAndCacheStoresFromDB();
    }

    @Test
    @DisplayName("스토어를 레디스에서 가져오기 + 스토어 캐싱")
    void testFindNearestStoreWithCache() throws InterruptedException {

        List<Callable<String>> tasks = new ArrayList<>();
        for (String uniqueAddress : uniqueAddresses) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest(uniqueAddress, "맥도날드");
            tasks.add(() -> customerService.findNearestStoreWithCache(request));
        }
        for(int i=0; i<100; i++) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest("경기도 하남시 미사강변동로 20", "맥도날드");
            tasks.add(() -> customerService.findNearestStoreWithCache(request));
        }

        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> result : results) {
            try {
                System.out.println("응답: " + result.get());
            } catch (ExecutionException e) {
                System.err.println("예외 발생: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("스토어를 레디스에서 가져오기X + 스토어 캐싱X")
    void testFindNearestStore() throws InterruptedException {

        List<Callable<String>> tasks = new ArrayList<>();
        for (String uniqueAddress : uniqueAddresses) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest(uniqueAddress, "맥도날드");
            tasks.add(() -> customerTestService.findNearestStore(request));
        }
        for(int i=0; i<100; i++) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest("경기도 하남시 미사강변동로 20", "맥도날드");
            tasks.add(() -> customerTestService.findNearestStore(request));
        }

        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> result : results) {
            try {
                System.out.println("응답: " + result.get());
            } catch (ExecutionException e) {
                System.err.println("예외 발생: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("스토어를 레디스에서 가져오기X + 스토어 캐싱")
    void testFindNearestStoreWithCacheInDB() throws InterruptedException {

        List<Callable<String>> tasks = new ArrayList<>();
        for (String uniqueAddress : uniqueAddresses) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest(uniqueAddress, "맥도날드");
            tasks.add(() -> customerTestService.findNearestStoreWithCache(request));
        }
        for(int i=0; i<100; i++) {
            CustomerRecommendStoreRequest request = new CustomerRecommendStoreRequest("경기도 하남시 미사강변동로 20", "맥도날드");
            tasks.add(() -> customerTestService.findNearestStoreWithCache(request));
        }

        ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> result : results) {
            try {
                System.out.println("응답: " + result.get());
            } catch (ExecutionException e) {
                System.err.println("예외 발생: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

}
