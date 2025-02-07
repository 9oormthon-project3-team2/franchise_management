package com.goorm.friendchise.domain.promotion.application;

import com.goorm.friendchise.domain.promotion.domain.Promotion;
import com.goorm.friendchise.domain.promotion.domain.PromotionRepository;
import com.goorm.friendchise.domain.notification.event.PromotionCreatedEvent;
import com.goorm.friendchise.domain.promotion.dto.request.PromotionCreateRequest;
import com.goorm.friendchise.domain.promotion.infrastructure.FakePromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTest {
	private PromotionService promotionService;
	private PromotionRepository promotionRepository;
	private ApplicationEventPublisher eventPublisher;

	@BeforeEach
	void setUp() {
		promotionRepository = new FakePromotionRepository();
		eventPublisher = mock(ApplicationEventPublisher.class);
		promotionService = new PromotionService(promotionRepository, eventPublisher);
	}

	@Test
	void createPromotion_success() {
		// given
		PromotionCreateRequest request = new PromotionCreateRequest(
			1L, "깜짝적 봄맞이 할인 이벤트", "전 제품 30% 할인!",
			LocalDateTime.of(2025, 3, 1, 9, 0),
			LocalDateTime.of(2025, 3, 7, 23, 59)
		);

		// when
		promotionService.createPromotion(request);

		// then
		List<Promotion> promotions = promotionRepository.findAll();
		assertThat(promotions).hasSize(1);
		assertThat(promotions.get(0).getTitle()).isEqualTo("깜짝적 봄맞이 할인 이벤트");

		// 이벤트가 정상적으로 발행되었는지 검증
		ArgumentCaptor<PromotionCreatedEvent> eventCaptor = ArgumentCaptor.forClass(PromotionCreatedEvent.class);
		verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());

		PromotionCreatedEvent event = eventCaptor.getValue();
		assertThat(event.getPromotion().getTitle()).isEqualTo("깜짝적 봄맞이 할인 이벤트");
	}
}
