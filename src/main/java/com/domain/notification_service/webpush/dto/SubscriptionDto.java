package com.domain.notification_service.webpush.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.domain.notification_service.webpush.entity.SubscriptionEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto implements Serializable {
    private Long id;
    private String user;
    private String endpoint;
    private String p256dh;
    private String auth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}