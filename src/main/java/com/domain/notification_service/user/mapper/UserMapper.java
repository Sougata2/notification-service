package com.domain.notification_service.user.mapper;

import com.domain.notification_service.user.dto.UserDto;
import com.domain.notification_service.user.entity.UserEntity;
import com.domain.notification_service.webpush.mapper.SubscriptionMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {SubscriptionMapper.class})
public interface UserMapper {
    @Mapping(target = "subscriptions", ignore = true)
    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "subscriptions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDto userDto, @MappingTarget UserEntity userEntity);
}