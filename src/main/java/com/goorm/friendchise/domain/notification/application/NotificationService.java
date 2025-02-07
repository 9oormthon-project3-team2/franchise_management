package com.goorm.friendchise.domain.notification.application;

import com.goorm.friendchise.domain.notification.domain.*;
import com.goorm.friendchise.domain.notification.event.PromotionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
	private final NotificationRepository repository;
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	@EventListener
	public void handlePromotionCreated(PromotionCreatedEvent promotion) {
		Long headquarterId = promotion.getPromotion().getHeadquarterId();
		String title = promotion.getPromotion().getTitle();
		String content = promotion.getPromotion().getContent();

		List<Long> storeIds = findStoresByHeadquarter(headquarterId);

		for (Long storeId : storeIds) {
			Notification notification = Notification.create(storeId, title, content);
			repository.save(notification);
			sendSse(storeId, title, content);
		}
	}

	private void sendSse(Long targetId, String title, String content) {
		SseEmitter emitter = emitters.get(targetId);
		if (emitter == null) return;

		try {
			emitter.send(SseEmitter.event().name("notification").data(content));
		} catch (IOException e) {
			emitters.remove(targetId);
		}
	}

	public SseEmitter subscribe(Long targetId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitters.put(targetId, emitter);
		emitter.onCompletion(() -> emitters.remove(targetId));
		emitter.onTimeout(() -> emitters.remove(targetId));
		return emitter;
	}

	public void markAsRead(Long notificationId) {
		Notification notification = repository.findById(notificationId)
			.orElseThrow(() -> new IllegalArgumentException("알림을 찾을 수 없습니다."));
		notification.markAsRead();
	}

	public void deleteNotification(Long notificationId) {
		repository.delete(notificationId);
	}

	// 본사에 속한 매장 ID 목록 조회 (예제용, 실제 DB 조회 필요)
	private List<Long> findStoresByHeadquarter(Long headquarterId) {
		return List.of(101L, 102L, 103L);
	}
}
