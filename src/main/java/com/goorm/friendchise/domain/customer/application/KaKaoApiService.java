package com.goorm.friendchise.domain.customer.application;

import com.goorm.friendchise.domain.customer.domain.Coordinates;
import com.goorm.friendchise.domain.customer.dto.response.KakaoAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KaKaoApiService {
    private final WebClient kakaoWebClient;

    public Coordinates getCoordinatesByAddress(String address) {
        KakaoAddressResponse response = kakaoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/address.json")
                        .queryParam("query", address)
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressResponse.class)
                .block();

        return response.getDocuments().stream()
                .findFirst()
                .map(doc -> new Coordinates(
                        Double.parseDouble(doc.getLatitude()), // 위도
                        Double.parseDouble(doc.getLongitude())  // 경도
                ))
                .orElseThrow(() -> new RuntimeException("주소를 좌표로 변환할 수 없습니다."));
    }
}