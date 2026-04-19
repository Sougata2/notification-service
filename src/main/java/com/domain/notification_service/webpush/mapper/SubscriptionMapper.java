package com.domain.notification_service.webpush.mapper;

import com.domain.notification_service.user.mapper.UserMapper;
import com.domain.notification_service.webpush.dto.SubscriptionDto;
import com.domain.notification_service.webpush.entity.SubscriptionEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface SubscriptionMapper {
    @Mapping(target = "user", ignore = true)
    SubscriptionEntity toEntity(SubscriptionDto subscriptionDto);

    @Mapping(target = "user", source = "user.email")
    SubscriptionDto toDto(SubscriptionEntity subscriptionEntity);

    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SubscriptionEntity partialUpdate(SubscriptionDto subscriptionDto, @MappingTarget SubscriptionEntity subscriptionEntity);
}