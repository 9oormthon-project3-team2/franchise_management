package com.goorm.friendchise.domain.customer.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .username("testUser")
                .password("testPass")
                .achievements(new HashSet<>())
                .build();
    }
    @Test
    @DisplayName("1. 객체 생성")
    void customer_객체_생성_테스트() {


        // Then
        assertThat(customer).isNotNull();
        assertThat(customer.getUsername()).isEqualTo("testUser");
        assertThat(customer.getPassword()).isEqualTo("testPass");
        assertThat(customer.getAchievements()).isEmpty();
    }

    @Test
    @DisplayName("2. 비밀번호 변경")
    void 비밀번호_변경_테스트() {


        // When
        customer.updatePassword("newPass");

        // Then
        assertThat(customer.getPassword()).isEqualTo("newPass");
    }

    @Test
    @DisplayName("3. 업적 추가 테스트")
    void 업적_추가_테스트() {
        // When
        customer.getAchievements().add(Achievement.GANGDONG_MASTER);

        // Then
        assertThat(customer.getAchievements()).contains(Achievement.GANGDONG_MASTER);
    }

    @Test
    @DisplayName("4. 중복 업적 추가 테스트")
    void 중복_업적_추가_테스트() {
        // When
        customer.getAchievements().add(Achievement.GANGDONG_MASTER);
        customer.getAchievements().add(Achievement.GANGDONG_MASTER);
        // Then
        assertThat(customer.getAchievements()).hasSize(1);
    }

}
