package com.domain.notification_service.client.auth;

import com.domain.notification_service.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @GetMapping("/users/user-info/{email}")
    UserDto getUserInfo(@PathVariable String email);

    @GetMapping("/users/id/{id}")
    UserDto getUserById(@PathVariable Long id);
}
