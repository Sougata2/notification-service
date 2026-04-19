package com.domain.notification_service.webpush.service;

import com.domain.notification_service.webpush.dto.SubscriptionRequestDto;

public interface SubscriptionService {
    void subscribe(SubscriptionRequestDto dto);

    void notifyUser(String email, String title, String body);
}
