package com.goorm.friendchise.domain.headquarter.dto.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChatCompletionStreamResponseDto(
        List<ChatCompletionStreamResponseDto> chatCompletionStreamResponseDtos,
        List<StreamChoice> choices
) {
    @Getter
    @RequiredArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StreamChoice {
        private final int index;
        private final Delta delta;

        public static StreamChoice of(int index, Delta delta) {
            return new StreamChoice(index, delta);
        }
    }

    @Getter
    @RequiredArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Delta {
        private final String content;
    }
}
