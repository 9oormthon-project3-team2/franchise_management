package com.goorm.friendchise.domain.headquarter.Item.domain;

import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import com.goorm.friendchise.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarter_id")
    private Headquarter headquarter;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    private int price;

    @Builder
    private Item(Headquarter headquarter, String name, int price) {
        this.headquarter = headquarter;
        this.name = name;
        this.price = price;
    }

    public static Item of(Headquarter headquarter, String name, int price) {
        return Item.builder()
                .headquarter(headquarter)
                .name(name)
                .price(price)
                .build();
    }
}
