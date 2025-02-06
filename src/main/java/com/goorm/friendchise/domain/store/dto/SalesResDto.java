package com.goorm.friendchise.domain.store.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class SalesResDto {

    private Long id;
    private LocalDate date;
    private String writer;
}
