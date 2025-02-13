package com.goorm.friendchise.domain.headquarter.application;

import com.goorm.friendchise.domain.headquarter.domain.Category;
import com.goorm.friendchise.domain.headquarter.domain.SubCategory;
import com.goorm.friendchise.domain.headquarter.dto.kakaomap.CategoryGroupCode;
import com.goorm.friendchise.domain.headquarter.dto.kakaomap.KakaoApiResultDto;
import com.goorm.friendchise.domain.headquarter.dto.kakaomap.KakaoPlaceDto;
import mockwebserver3.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class KakaoApiServiceTest {
    @Mock
    private KakaoApiClient kakaoApiClient;

    @InjectMocks
    private KakaoApiService kakaoApiService;

//    @Test
//    @DisplayName("반경 500m 내 동일한 프랜차이즈 매장이 존재할 경우")
//    void getTotalPlaceData_FranchiseStoreExistx() {
//
//        when(kakaoApiClient.requestPlaceDataByKeywordSync(anyString(), anyDouble(), anyDouble(), eq(500)))
//                .thenReturn(new KakaoApiResultDto(Collections.emptyList())); // 동일 프랜차이즈 매장이 없다고 가정
//
//        when(kakaoApiClient.requestPlaceDataByKeywordAsync(eq("TestCategory"), anyDouble(), anyDouble(), eq(1000)))
//                .thenReturn(Mono.just(categoryDummyResult));
//
//        when(kakaoApiClient.requestPlaceDataByKeywordAsync(eq("버스정류장"), anyDouble(), anyDouble(), eq(200)))
//                .thenReturn(Mono.just(busStopDummy));
//
//        when(kakaoApiClient.requestPlaceDataByCategoryAsync(eq(CategoryGroupCode.SUBWAY.getCode()), anyDouble(), anyDouble(), eq(500)))
//                .thenReturn(Mono.just(subwayDummy));
//
//// 사용자 선택 카테고리에 대한 스텁도 인자에 따라 별도로 설정
//        when(kakaoApiClient.requestPlaceDataByCategoryAsync(eq("SomeCategoryCode"), anyDouble(), anyDouble(), eq(500)))
//                .thenReturn(Mono.just(userCategoryDummyResult));
//    }

    @Test
    @DisplayName("반경 500m 내 동일한 프랜차이즈 매장이 존재할 경우 null을 반환한다.")
    void getTotalPlaceData_FranchiseStoreExist() {

        when(kakaoApiClient.requestPlaceDataByKeywordSync(anyString(), anyDouble(), anyDouble(), eq(500)))
                .thenReturn(new KakaoApiResultDto(List.of(new KakaoPlaceDto("120")))); // 동일 프랜차이즈 매장이 있음

        Mono<Map<String, KakaoApiResultDto>> totalPlaceData = kakaoApiService.getTotalPlaceData(
                "맥도날드",
                Category.FASTFOOD,
                SubCategory.NONE,
                List.of("백화점", "학교"),
                37.55185670851289,
                126.96979548002724
        );

        assertThat(totalPlaceData).isNull();
    }
}