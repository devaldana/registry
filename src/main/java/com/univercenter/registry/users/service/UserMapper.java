package com.univercenter.registry.users.service;

import com.univercenter.registry.users.controller.dtos.SaveUserRequest;
import com.univercenter.registry.users.controller.dtos.UserRegistrationInfo;
import com.univercenter.registry.users.repository.Phone;
import com.univercenter.registry.users.repository.User;

import java.time.OffsetDateTime;

import static java.util.stream.Collectors.toSet;

final class UserMapper {

    private UserMapper() {}

    public static User convertToUser(
            final SaveUserRequest request,
            final String encodedPassword,
            final String token,
            final boolean active)
    {
        final var now = OffsetDateTime.now();
        final var phones = request.phones().stream().map(UserMapper::convertToPhone).collect(toSet());
        final var user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(encodedPassword);
        user.setCreatedAt(now);
        user.setModifiedAt(now);
        user.setLastLoginAt(now);
        user.setActive(active);
        user.setToken(token);
        user.setPhones(phones);
        return user;
    }

    private static Phone convertToPhone(final SaveUserRequest.Phone phone) {
        return new Phone(phone.number(), phone.cityCode(), phone.countryCode());
    }

    public static UserRegistrationInfo convertToUserRegistrationInfo(final User user) {
        return new UserRegistrationInfo(
                user.getId(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getLastLoginAt(),
                user.isActive(),
                user.getToken()
        );
    }
}
