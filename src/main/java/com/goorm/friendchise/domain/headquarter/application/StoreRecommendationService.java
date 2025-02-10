package com.goorm.friendchise.domain.headquarter.application;

import com.goorm.friendchise.domain.headquarter.dto.kakaomap.KakaoApiResultDto;
import com.goorm.friendchise.domain.headquarter.dto.kakaomap.KakaoPlaceDto;
import com.goorm.friendchise.domain.headquarter.dto.openai.ChatCompletionResponseDto;
import com.goorm.friendchise.domain.headquarter.util.JsonHashMapConverter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StoreRecommendationService {
    private final KakaoApiService kakaoApiService;
    private final OpenAiApiService openAiApiService;

    // 읽기 작업만 하므로 ConcurrentHashMap 대신 unmodifiableMap 사용
    private Map<String, Map<String, Integer>> rentalFeeMap;

    /*
     * 지역별 임대료 데이터인 rentalData.json 파일을 읽어 ConcurrentHashMap에 저장한다.
     */
    @PostConstruct
    public void initRentalFeeMap() throws IOException {
        // TODO: 상권에 따른 임대료 데이터 파일을 읽어와서 메모리에 로드한다.
        String jsonFile = "rentalData.json";
        rentalFeeMap = Collections.unmodifiableMap(JsonHashMapConverter.convertJsonToHashmap(jsonFile));
    }


    // TODO: y, x 대신 상권을 String으로 받아서 rentalFeeMap에서 해당 상권의 임대료 데이터를 가져오기
    public ChatCompletionResponseDto getRecommendation(List<String> userSelectedCategory, Double y, Double x) {
        Mono<Map<String, KakaoApiResultDto>> mono = kakaoApiService.getTotalPlaceData(userSelectedCategory, y, x);
        Map<String, KakaoApiResultDto> totalPlaceData = mono.block();

    //        String address = totalPlaceData.get("반경 200m 내 버스정류장").documents().get(0).addressName();

        // 카카오 API로부터 받은 데이터를 모아서 OpenAI API에 요청하여 추천 점수를 받아온다.
        StringBuilder sb = new StringBuilder();
        totalPlaceData.entrySet().forEach(entry -> {
            List<KakaoPlaceDto> documents = entry.getValue().documents();
            sb.append(entry.getKey()).append(": [");
            documents.stream().map(KakaoPlaceDto::distance).forEach(distance -> sb.append(distance).append(", "));
            sb.append("]\n");
        });

        return openAiApiService.requestChatCompletionApi(sb.toString());
    }
}
