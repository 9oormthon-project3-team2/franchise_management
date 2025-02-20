package com.goorm.friendchise.domain.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationDeletedEvent {
    private final Long notificationId;
}
