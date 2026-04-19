package com.domain.notification_service.webpush.repository;

import com.domain.notification_service.webpush.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    @Query("select s from SubscriptionEntity s where s.endpoint = :endpoint and s.user.email = :email")
    Optional<SubscriptionEntity> findByEndpointAndUserEmail(String endpoint, String userEmail);

    @Query("select s from SubscriptionEntity s where s.user.email = :email")
    List<SubscriptionEntity> findByUserEmail(String email);
}