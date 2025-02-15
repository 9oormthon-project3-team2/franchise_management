package com.goorm.friendchise.domain.promotion.application;

import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.Role;
import com.goorm.friendchise.domain.promotion.domain.Promotion;
import com.goorm.friendchise.domain.promotion.domain.PromotionRepository;
import com.goorm.friendchise.domain.notification.event.PromotionCreatedEvent;
import com.goorm.friendchise.domain.promotion.dto.request.PromotionCreateRequest;
import com.goorm.friendchise.domain.promotion.dto.response.PromotionDetailResponse;
import com.goorm.friendchise.global.auth.application.AuthService;
import com.goorm.friendchise.global.exception.CustomException;
import com.goorm.friendchise.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PromotionService {
	private final PromotionRepository promotionRepository;
	private final ApplicationEventPublisher eventPublisher;
	private final AuthService authService;

	public void createPromotion(PromotionCreateRequest request) {
		Manager manager = authService.findManagerByAuth();

		if (manager.getRole() != Role.HEADQUARTER) {
			throw new CustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
		}

		Long headquarterId = manager.getManageId();

		if (headquarterId == null) {
			throw new CustomException(ErrorCode.HEADQUARTER_NOT_FOUND);
		}

		Promotion promotion = Promotion.create(headquarterId, request.title(), request.content(), request.startDate(), request.endDate());
		promotionRepository.save(promotion);

		log.info("프로모션 저장 완료: {}", promotion.getTitle());
		eventPublisher.publishEvent(new PromotionCreatedEvent(promotion));
		log.info("프로모션 이벤트 발행 완료: {}", promotion.getTitle());
	}

	@Transactional(readOnly = true)
	public List<PromotionDetailResponse> getMyHeadquarterPromotions() {
		Manager manager = authService.findManagerByAuth();
		Long headquarterId = manager.getManageId();

		if (headquarterId == null) {
			throw new IllegalStateException("본사 정보가 없습니다.");
		}

		return promotionRepository.findByHeadquarterId(headquarterId).stream()
			.map(promotion -> PromotionDetailResponse.builder()
				.id(promotion.getId())
				.title(promotion.getTitle())
				.content(promotion.getContent())
				.startDate(promotion.getStartDate())
				.endDate(promotion.getEndDate())
				.build())
			.collect(Collectors.toList());
	}
}
