package com.goorm.friendchise.domain.notification.event;

import com.goorm.friendchise.domain.notification.domain.Notification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationReadEvent {
    private final Notification notification;
}
