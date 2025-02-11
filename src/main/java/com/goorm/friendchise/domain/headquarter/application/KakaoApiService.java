package com.goorm.friendchise.domain.headquarter.application;

import com.goorm.friendchise.domain.headquarter.dto.kakaomap.KakaoApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KakaoApiService {
    private final WebClient webClient;

    /*
        * "키워드로 장소 검색하기" 카카오 지도 API를 사용하여 radius 반경 내 장소 정보를 가져온다. (동기)
        * @param keyword 검색 키워드
        * @param y 위도
        * @param x 경도
        * @param radius 검색 반경
        * @return KakaoSearchResDto
    */
    public KakaoApiResultDto requestPlaceDataByKeywordSync(String keyword, Double y, Double x, int radius) {
        String uri = makeKeywordSearchAPIUri(keyword, y, x, radius);

        // TODO: 예외처리, 재시도 등 로직 필요
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoApiResultDto.class)
                .block();
    }

    /*
     * "키워드로 장소 검색하기" 카카오 지도 API를 사용하여 radius 반경 내 장소 정보를 가져온다. (비동기)
     * @param keyword 검색 키워드
     * @param y 위도
     * @param x 경도
     * @param radius 검색 반경
     * @return Mono<KakaoSearchResDto>
     */
    public Mono<KakaoApiResultDto> requestPlaceDataByKeywordAsync(String keyword, Double y, Double x, int radius) {
        String uri = makeKeywordSearchAPIUri(keyword, y, x, radius);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoApiResultDto.class);
    }

    /*
     * "카테고리로 장소 검색하기" 카카오 지도 API를 사용하여 radius 반경 내 장소 정보를 가져온다. (동기)
     * @param categoryGroupCode 카테고리 그룹 코드
     * @param y 위도
     * @param x 경도
     * @param radius 검색 반경
     * @return KakaoSearchResDto
     */
    public KakaoApiResultDto requestPlaceDataByCategorySync(String categoryGroupCode, Double y, Double x, int radius) {
        String uri = makeCategorySearchAPIUri(categoryGroupCode, y, x, radius);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoApiResultDto.class)
                .block();
    }

    /*
        * "카테고리로 장소 검색하기" 카카오 지도 API를 사용하여 radius 반경 내 장소 정보를 가져온다. (비동기)
        * @param categoryGroupCode 카테고리 그룹 코드
        * @param y 위도
        * @param x 경도
        * @param radius 검색 반경
        * @return Mono<KakaoSearchResDto>
     */
    public Mono<KakaoApiResultDto> requestPlaceDataByCategoryAsync(String categoryGroupCode, Double y, Double x, int radius) {
        String uri = makeCategorySearchAPIUri(categoryGroupCode, y, x, radius);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(KakaoApiResultDto.class);
    }

    public Mono<Map<String, KakaoApiResultDto>> getTotalPlaceData(List<String> userSelectedCategory, Double y, Double x) {
        // TODO: franchiseName, category, subCategory SecurityContextHolder 에서 가져와서 keyword로 사용

        // TODO: 각 api 호출 프로세스를 메소드로 분리하는 리팩토링
        // sample data
        String franchiseName = "맥도날드";
        String category = "패스트푸드";
        Optional<String> subCategory = null;

        // TODO: 0. x, y로 법정동 주소 가져오기

        // 1. 동일 프랜차이즈 매장 검색
        KakaoApiResultDto sameFranchiseStoreResult = requestPlaceDataByKeywordSync(franchiseName, y, x, 500);
        if(!sameFranchiseStoreResult.documents().isEmpty()) {
            return null; // 동일 프랜차이즈 매장이 존재하면 바로 리턴
        }

        Map<String, Mono<KakaoApiResultDto>> totalSearchResults = new HashMap();

        // 2. 동일 업종 경쟁 매장 검색
        Mono<KakaoApiResultDto> sameCategoryStoreResult;
        if(!subCategory.isPresent()) { // subCategory가 없으면 category로 검색
            sameCategoryStoreResult =  requestPlaceDataByKeywordAsync(category, y, x, 1000);
        } else {
            sameCategoryStoreResult = requestPlaceDataByKeywordAsync(subCategory.get(), y, x, 1000);
        }
        totalSearchResults.put("반경 1km 내 동일 업종 경쟁 매장", sameCategoryStoreResult);

        // 3. 버스 정류장, 지하철역 검색
        Mono<KakaoApiResultDto> busStopResult = requestPlaceDataByKeywordAsync("버스정류장", y, x, 200);
        Mono<KakaoApiResultDto> subwayStationResult = requestPlaceDataByKeywordAsync("지하철역", y, x, 500);
        totalSearchResults.put("반경 200m 내 버스정류장", busStopResult);
        totalSearchResults.put("반경 500m 내 지하철역", subwayStationResult);

        // 4. 사용자가 선택한 카테고리로 검색
        for(String selectedCategory : userSelectedCategory) {
            Mono<KakaoApiResultDto> userDefinedResult = requestPlaceDataByKeywordAsync(selectedCategory, y, x, 500);
            totalSearchResults.put("반경 500m 내 " + selectedCategory, userDefinedResult);
        }

        // 5. 결과 합치기
        List<Mono<KakaoApiResultDto>> monos = new ArrayList<>(totalSearchResults.values());
        List<String> keys = new ArrayList<>(totalSearchResults.keySet());

        return Mono.zip(monos, results -> {
            Map<String, KakaoApiResultDto> combinedResults = new HashMap<>();
            for (int i = 0; i < results.length; i++) {
                combinedResults.put(keys.get(i), (KakaoApiResultDto) results[i]);
            }
            return combinedResults;
        });

    }


    // 키워드로 장소 검색하기 API URI 생성
    private static String makeKeywordSearchAPIUri(String keyword, Double y, Double x, int radius) {
        return UriComponentsBuilder.fromPath("/search/keyword.json")
                .queryParam("query", keyword)
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("radius", radius) // 값 조정 필요
                .queryParam("size", 10)
                .queryParam("sort", "distance")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

    // 카테고리로 장소 검색하기 API URI 생성
    private static String makeCategorySearchAPIUri(String categoryGroupCode, Double y, Double x, int radius) {
        return UriComponentsBuilder.fromPath("/search/category.json")
                .queryParam("category_group_code", categoryGroupCode)
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("radius", radius) // 값 조정 필요
                .queryParam("size", 10)
                .queryParam("sort", "distance")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

}
