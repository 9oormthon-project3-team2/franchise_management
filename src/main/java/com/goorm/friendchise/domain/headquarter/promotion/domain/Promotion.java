package com.goorm.friendchise.domain.headquarter.promotion.domain;

import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import com.goorm.friendchise.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Promotion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "headquarter_id", nullable = false)
    private Headquarter headquarter;

    @NotNull
    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String content;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
