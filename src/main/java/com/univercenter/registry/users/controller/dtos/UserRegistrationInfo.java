package com.univercenter.registry.users.controller.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserRegistrationInfo(
        UUID id,
        OffsetDateTime createdAt,
        OffsetDateTime modifiedAt,
        OffsetDateTime lastLoginAt,
        boolean isActive,
        String token
) {}
