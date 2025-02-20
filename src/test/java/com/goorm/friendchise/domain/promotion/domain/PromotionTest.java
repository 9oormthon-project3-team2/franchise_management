package com.goorm.friendchise.domain.promotion.domain;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionTest {

	@Test
	void create_success() {
		// given
		Long headquarterId = 1L;
		String title = "깜짝적 봄맞이 할인 이벤트";
		String content = "전 제품 30% 할인!";
		LocalDateTime startDate = LocalDateTime.of(2025, 3, 1, 9, 0);
		LocalDateTime endDate = LocalDateTime.of(2025, 3, 7, 23, 59);

		Manager headquarterManager = Manager.builder()
			.role(Role.HEADQUARTER)
			.manageId(headquarterId)
			.build();

		// when
		Promotion promotion = Promotion.create(headquarterManager, title, content, startDate, endDate);

		// then
		assertThat(promotion).isNotNull();
		assertThat(promotion.getHeadquarterId()).isEqualTo(headquarterId);
		assertThat(promotion.getTitle()).isEqualTo(title);
		assertThat(promotion.getContent()).isEqualTo(content);
		assertThat(promotion.getStartDate()).isEqualTo(startDate);
		assertThat(promotion.getEndDate()).isEqualTo(endDate);
	}
}
