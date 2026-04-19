package com.domain.notification_service.webpush.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "vapid")
public class WebPushProperties {
    private String publicKey;
    private String privateKey;
    private String subject;
}
