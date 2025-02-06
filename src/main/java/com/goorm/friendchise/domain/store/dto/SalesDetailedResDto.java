package com.goorm.friendchise.domain.store.dto;

import com.goorm.friendchise.domain.store.domain.Sales;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SalesDetailedResDto {

    private Long id;
    private LocalDate date;
    private Long dailySales;
    private String writer;

    public SalesDetailedResDto(Sales sales) {
        this.id = sales.getId();
        this.date = sales.getDate();
        this.dailySales = sales.getDailySales();
        this.writer = sales.getWriter();
    }
}
