package com.goorm.friendchise.domain.headquarter.commercialarea;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Polygon;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "commercial_area",
        indexes = {
                @Index(name = "geom", columnList = "geom")
        }
)
public class CommercialArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, length = 50)
    private String areaName;

    @NotNull
    private BigDecimal rentalFee;

    @NotNull
    private Polygon geom;
}
