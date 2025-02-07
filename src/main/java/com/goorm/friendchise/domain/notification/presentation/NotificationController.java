package com.goorm.friendchise.domain.notification.presentation;

import com.goorm.friendchise.domain.notification.application.NotificationService;
import com.goorm.friendchise.domain.notification.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	//  알림 읽음 처리
	@PatchMapping("/{notificationId}/read")
	public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long notificationId) {
		notificationService.markAsRead(notificationId);
		return ResponseEntity.ok(new NotificationResponse(notificationId, "success", "알림이 읽음 처리되었습니다."));
	}

	//  알림 삭제
	@DeleteMapping("/{notificationId}")
	public ResponseEntity<NotificationResponse> deleteNotification(@PathVariable Long notificationId) {
		notificationService.deleteNotification(notificationId);
		return ResponseEntity.ok(new NotificationResponse(notificationId, "success", "알림이 삭제되었습니다."));
	}

	// SSE 구독 (매장 실시간 알림 받기)
	@GetMapping("/subscribe/{targetId}")
	public SseEmitter subscribe(@PathVariable Long targetId) {
		return notificationService.subscribe(targetId);
	}
}
