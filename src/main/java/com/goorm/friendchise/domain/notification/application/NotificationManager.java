package com.goorm.friendchise.domain.notification.application;

import com.goorm.friendchise.domain.customer.exception.CustomerException;
import com.goorm.friendchise.domain.headquarter.dto.store.StoreIdDto;
import com.goorm.friendchise.domain.manager.domain.Manager;
import com.goorm.friendchise.domain.manager.domain.Role;
import com.goorm.friendchise.domain.notification.domain.Notification;
import com.goorm.friendchise.domain.notification.domain.NotificationRepository;
import com.goorm.friendchise.domain.notification.dto.response.ReceivedNotificationResponse;
import com.goorm.friendchise.global.auth.application.AuthService;
import com.goorm.friendchise.global.exception.CustomException;
import com.goorm.friendchise.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationManager {
	private final NotificationRepository repository;
	private final AuthService authService;

	private Manager getAuthManager() {
		return authService.findManagerByAuth();
	}

	private Manager getAuthStoreManager() {
		Manager authManager = getAuthManager();
		if (authManager.getRole() != Role.STORE) {
			throw new CustomException(ErrorCode.NO_STORE_AUTHENTICATION_ERROR);
		}
		return authManager;
	}

	public List<Notification> createNotifications(List<StoreIdDto> storeIds, String title, String content) {
		List<Notification> notifications = storeIds.stream()
			.map(storeId -> Notification.create(storeId.id(), title, content))
			.collect(Collectors.toList());

		saveAllNotification(notifications);
		return notifications;
	}

	@Transactional
	public void saveAllNotification(List<Notification> notifications) {
		repository.saveAll(notifications);
		log.info("알림 {}개 저장 완료", notifications.size());
	}

	@Transactional
	public void markAsRead(Long notificationId) {
		Notification notification = repository.findById(notificationId)
			.orElseThrow(() -> new IllegalArgumentException("알림을 찾을 수 없습니다."));
		notification.markAsRead();
	}

	@Transactional
	public void deleteNotification(Long notificationId) {
		repository.deleteById(notificationId);
	}

	@Transactional
	public List<ReceivedNotificationResponse> getNotifications() {
		Manager manager = authService.findManagerByAuth();

		if (manager.getRole() != Role.STORE) {
			throw new CustomerException(ErrorCode.NO_STORE_AUTHENTICATION_ERROR);
		}

		Long storeId = manager.getManageId();

		if (storeId == null) {
			throw new CustomerException(ErrorCode.STORE_NOT_FOUND);
		}

		List<Notification> notifications = repository.findByStoreId(storeId);
		return notifications.stream()
			.map(notification -> ReceivedNotificationResponse.builder()
				.title(notification.getTitle())
				.content(notification.getContent())
				.isRead(notification.isRead())
				.build())
			.collect(Collectors.toList());
	}
}
