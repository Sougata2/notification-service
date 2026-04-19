package com.domain.notification_service.user.entity;

import com.domain.notification_service.webpush.entity.SubscriptionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    /*
     * RELATIONS
     * */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<SubscriptionEntity> subscriptions;
}