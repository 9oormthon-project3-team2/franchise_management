package com.goorm.friendchise.domain.notification.application;

import com.goorm.friendchise.domain.headquarter.dto.store.StoreIdDto;
import com.goorm.friendchise.domain.notification.domain.Notification;
import com.goorm.friendchise.domain.notification.dto.response.NotificationDetailResponse;
import com.goorm.friendchise.domain.notification.infrastructure.FakeNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationManagerTest {
	private NotificationManager notificationManager;
	private FakeNotificationRepository repository;

	@BeforeEach
	void setUp() {
		repository = new FakeNotificationRepository();
		notificationManager = new NotificationManager(repository);
	}

	@Test
	@DisplayName("알림을 생성하고 저장할 수 있다.")
	void createNotifications() {
		// Given
		List<StoreIdDto> storeIds = List.of(new StoreIdDto(101L), new StoreIdDto(102L));
		String title = "New Promotion";
		String content = "Promotion Details";

		// When
		List<Notification> notifications = notificationManager.createNotifications(storeIds, title, content);

		// Then
		assertThat(notifications).hasSize(2);
		assertThat(repository.findAll()).hasSize(2);
	}

	@Test
	@DisplayName("특정 타겟 ID로 알림을 조회할 수 있다.")
	void getNotificationsByTarget() {
		// Given
		Notification notification1 = repository.save(Notification.create(10101L, "Title1", "Content1"));
		Notification notification2 = repository.save(Notification.create(10101L, "Title2", "Content2"));
		Notification notification3 = repository.save(Notification.create(10102L, "Title2", "Content2"));

		// When
		List<NotificationDetailResponse> foundNotifications = notificationManager.getNotificationsByTarget(10101L);

		// Then
		assertThat(foundNotifications).hasSize(2);
	}

	@Test
	@DisplayName("알림을 읽음 처리할 수 있다.")
	void markAsRead() {
		// Given
		Notification notification = repository.save(Notification.create(101L, "Title", "Content"));
		Long notificationId = notification.getId();

		// When
		notificationManager.markAsRead(notificationId);

		// Then
		Notification updatedNotification = repository.findById(notificationId).orElseThrow();
		assertThat(updatedNotification.isRead()).isTrue();
	}

	@Test
	@DisplayName("알림을 삭제할 수 있다.")
	void deleteNotification() {
		// Given
		Notification notification = repository.save(Notification.create(101L, "Title", "Content"));
		Long notificationId = notification.getId();

		// When
		notificationManager.deleteNotification(notificationId);

		// Then
		assertThat(repository.findById(notificationId)).isEmpty();
	}
}
