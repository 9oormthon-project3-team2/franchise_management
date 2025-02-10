package com.goorm.friendchise.domain.headquarter.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class HeadquarterUserDetailsImpl implements UserDetails {
    private final Long id;
    private final String franchiseName;
    private final String category;
    private final String subCategory;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_HEADQUARTER"));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return franchiseName;
    }

    @Builder
    private HeadquarterUserDetailsImpl(Long id, String franchiseName, String category, String subCategory) {
        this.id = id;
        this.franchiseName = franchiseName;
        this.category = category;
        this.subCategory = subCategory;
    }

    public static HeadquarterUserDetailsImpl fromEntity(Headquarter headquarter) {
        return HeadquarterUserDetailsImpl.builder()
                .id(headquarter.getId())
                .franchiseName(headquarter.getFranchiseName())
                .category(headquarter.getCategory())
                .subCategory(headquarter.getSubCategory())
                .build();
    }
}
