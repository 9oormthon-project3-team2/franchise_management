package com.goorm.friendchise.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${kakao.apiKey}")
    private String authorization;

    @Value("${OPENAI_API_KEY}")
    private String openaiAuthorization;

    @Bean
    @Primary
    public WebClient webClient() {
        String apiKey = "KakaoAK " + authorization;

        return WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Authorization", apiKey);
                    httpHeaders.set("X-Requested-With", "curl");
                })
                .build();
    }

    @Bean
    @Qualifier("openaiWebClient")
    public WebClient openaiWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + openaiAuthorization);
                    httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }


}
