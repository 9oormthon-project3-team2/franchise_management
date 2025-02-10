package com.goorm.friendchise.domain.notification.domain;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
	List<Notification> findAll();

	Notification save(Notification notification);

	Optional<Notification> findById(Long id);

	void delete(Long id);
}
