package com.domain.notification_service.webpush.service.impl;

import com.domain.notification_service.client.auth.AuthClient;
import com.domain.notification_service.user.dto.UserDto;
import com.domain.notification_service.user.entity.UserEntity;
import com.domain.notification_service.user.mapper.UserMapper;
import com.domain.notification_service.user.repository.UserRepository;
import com.domain.notification_service.webpush.dto.SubscriptionRequestDto;
import com.domain.notification_service.webpush.entity.SubscriptionEntity;
import com.domain.notification_service.webpush.properties.WebPushProperties;
import com.domain.notification_service.webpush.repository.SubscriptionRepository;
import com.domain.notification_service.webpush.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final UserRepository userRepository;
    private final WebPushProperties properties;
    private final AuthClient authClient;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void subscribe(SubscriptionRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = saveOrGetUser(username);

        repository.findByEndpointAndUserEmail(dto.getEndpoint(), username).ifPresentOrElse(existing -> {
            // already subscribed to push notification
        }, () -> {
            SubscriptionEntity subscription = SubscriptionEntity.builder()
                    .user(user)
                    .endpoint(dto.getEndpoint())
                    .p256dh(dto.getKeys().getP256dh())
                    .auth(dto.getKeys().getAuth())
                    .build();
            repository.save(subscription);
        });
    }

    @Async
    @Override
    public void notifyUser(String email, String title, String body) {
        List<SubscriptionEntity> subscriptions = repository.findByUserEmail(email);
        try {
            for (SubscriptionEntity subscription : subscriptions) {
                Notification notification = getNotification(title, body, subscription);
                PushService pushService = new PushService();
                pushService.setPublicKey(properties.getPublicKey());
                pushService.setPrivateKey(properties.getPrivateKey());
                pushService.setSubject(properties.getSubject());
                pushService.send(notification);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Notification getNotification(String title, String body, SubscriptionEntity subscription) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        Subscription.Keys keys = new Subscription.Keys(subscription.getP256dh(), subscription.getAuth());
        Subscription sub = new Subscription(subscription.getEndpoint(), keys);
        String payload = """
                {
                "title": "%s",
                "body": "%s",
                "url": "/chat"
                }
                """.formatted(title, body);
        return new Notification(sub, payload);
    }

    @Transactional
    public UserEntity saveOrGetUser(String email) {
        Optional<UserEntity> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            return existing.get();
        }
        UserDto dto = authClient.getUserInfo(email);
        UserEntity entity = userMapper.toEntity(dto);
        return userRepository.save(entity);
    }
}
