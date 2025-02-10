
package com.goorm.friendchise.domain.notification.event;

import com.goorm.friendchise.domain.promotion.domain.Promotion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromotionCreatedEvent {
    private final Promotion promotion;
}
