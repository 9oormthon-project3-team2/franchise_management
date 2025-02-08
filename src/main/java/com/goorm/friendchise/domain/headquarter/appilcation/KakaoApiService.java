package com.goorm.friendchise.domain.headquarter.appilcation;

import com.goorm.friendchise.domain.headquarter.dto.kakao.KakaoApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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
        // TODO: securityContextHolder에서 franchiseName 가져와서 keyword로 사용
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

    public void getTotalPlaceData() {
        KakaoApiResultDto result = requestPlaceDataByKeywordSync("맥도날드", 37.566826, 126.9786567, 500);
        result.documents().forEach(System.out::println);
    }


    // 키워드로 장소 검색하기 API URI 생성
    private static String makeKeywordSearchAPIUri(String keyword, Double y, Double x, int radius) {
        return UriComponentsBuilder.fromPath("/search/keyword.json")
                .queryParam("query", keyword)
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("radius", radius) // 값 조정 필요
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
                .queryParam("sort", "distance")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

}
