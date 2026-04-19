package com.domain.notification_service.webpush.controller;

import com.domain.notification_service.webpush.dto.NotificationDto;
import com.domain.notification_service.webpush.dto.SubscriptionRequestDto;
import com.domain.notification_service.webpush.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web-push")
public class WebPushController {
    private final SubscriptionService service;

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriptionRequestDto dto) {
        service.subscribe(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notify")
    public ResponseEntity<Void> notify(@RequestBody NotificationDto dto) {
        for (String email : dto.getEmails()) {
            service.notifyUser(email, dto.getTitle(), dto.getBody());
        }
        return ResponseEntity.ok().build();
    }
}
