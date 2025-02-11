package com.goorm.friendchise.domain.store.domain;

import com.goorm.friendchise.domain.headquarter.domain.Headquarter;
import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.store.dto.StoreReqDto;
import com.goorm.friendchise.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String dong;
    private Double pointX;
    private Double pointY;
    private String franchiseName;

    @Column(name = "manage_id")
    private Long manageId;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Sales> salesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "headquarter_id", nullable = false)
    private Headquarter headquarter;

    // 양방향 연관관계 편의 메소드, Store 저장할 때 한번 호출 필요
    public void setHeadquarter(Headquarter headquarter) {
        this.headquarter = headquarter;
        headquarter.addStore(this);
    }

    @Builder
    public Store(StoreReqDto dto, Headquarter headquarter, Manager manager) {
        this.address = dto.address();
        this.dong = dto.dong();
        this.pointX = dto.x();
        this.pointY = dto.y();
        this.franchiseName = dto.franchiseName();
        this.manageId = manager.getId();
        setHeadquarter(headquarter);
    }

    public void updateStore(StoreReqDto reqDto, Headquarter headquarter) {
        this.address = reqDto.address();
        this.dong = reqDto.dong();
        this.pointX = Double.valueOf(reqDto.x());
        this.pointY = Double.valueOf(reqDto.y());
        this.franchiseName = reqDto.franchiseName();
        setHeadquarter(headquarter);
    }
}
