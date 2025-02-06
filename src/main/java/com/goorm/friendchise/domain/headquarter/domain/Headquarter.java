package com.goorm.friendchise.domain.headquarter.domain;

import com.goorm.friendchise.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Headquarter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, length = 50)
    private String franchiseName;


    public static Headquarter of(String franchiseName) {
        return Headquarter.builder()
                .franchiseName(franchiseName)
                .build();
    }

    public void updateFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }
}
