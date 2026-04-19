package com.domain.notification_service.user.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.domain.notification_service.user.entity.UserEntity}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}