package com.goorm.friendchise.domain.location.domain;

import com.goorm.friendchise.domain.customer.domain.Customer;
import com.goorm.friendchise.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Double startY;  // 출발지 위도

    @Column(nullable = false)
    private Double startX; // 출발지 경도

    private Double destinationY;  // 도착지 위도

    private Double destinationX; // 도착지 경도

    @Column(nullable = false)
    private LocalDateTime recordedAt;



    public void setDestination(Double destinationX,Double destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }
}