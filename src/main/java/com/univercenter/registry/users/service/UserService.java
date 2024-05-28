package com.univercenter.registry.users.service;

import com.univercenter.registry.common.exceptions.DatabaseException;
import com.univercenter.registry.security.SecurityService;
import com.univercenter.registry.users.controller.dtos.SaveUserRequest;
import com.univercenter.registry.users.controller.dtos.UserRegistrationInfo;
import com.univercenter.registry.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final boolean DEFAULT_ACTIVATION_STATUS = false;

    private final SecurityService securityService;
    private final UserRepository userRepository;

    public UserRegistrationInfo save(final SaveUserRequest request) {
        final var token = securityService.generateToken(request.name(), DEFAULT_ACTIVATION_STATUS);
        final var encodePassword = securityService.encodePassword(request.password());
        final var user = UserMapper.convertToUser(request, encodePassword, token, DEFAULT_ACTIVATION_STATUS);

        try {
            final var savedUser = this.userRepository.save(user);
            return UserMapper.convertToUserRegistrationInfo(savedUser);
        } catch (final Exception _) {
            throw new DatabaseException("Database error while persisting user");
        }
    }
}
