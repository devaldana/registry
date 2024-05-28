package com.univercenter.registry.users.controller;

import com.univercenter.registry.common.exceptions.BadRequestException;
import com.univercenter.registry.users.Config;
import com.univercenter.registry.users.controller.dtos.SaveUserRequest;
import com.univercenter.registry.users.controller.dtos.SaveUserRequest.Phone;
import com.univercenter.registry.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.univercenter.registry.common.StringUtils.*;

@Component
@RequiredArgsConstructor
public class UserControllerHelper {

    private final Config config;
    private final UserRepository userRepository;

    public void validateSaveUserRequest(final SaveUserRequest request) {
        validateRequestBody(request);
        validateStringsAreNotBlank(request.name(), request.email(), request.password());
        validateEmail(request.email());
        validatePassword(request.password());
        validatePhones(request.phones());
    }

    private static void validateRequestBody(final SaveUserRequest request) {
        if (request == null) {
            throw new BadRequestException("Empty request body not allowed");
        }
    }

    private void validateStringsAreNotBlank(final String... args) {
        for(final var arg : args) {
            if (isBlank(arg)) {
                throw new BadRequestException("Request is missing required arguments");
            }
        }
    }

    private void validateEmail(final String email) {
        if(!isValidEmail(email)){
            throw new BadRequestException("Email format is invalid");
        }
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email is already in use");
        }
    }

    private void validatePassword(final String password) {
        if (!matches(password, config.passwordRegex())) {
            throw new BadRequestException("Password do not match requirements");
        }
    }

    private void validatePhones(final Set<Phone> phones) {
        if (phones == null || phones.isEmpty()) {
            throw new BadRequestException("Required data is missing");
        }
        phones.forEach(this::validatePhone);
    }

    private void validatePhone(final Phone phone) {
        if (phone == null  || isBlank(phone.number()) || isBlank(phone.countryCode()) || isBlank(phone.cityCode())) {
            throw new BadRequestException("Missing required arguments for phone");
        }
    }
}
