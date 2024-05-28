package com.univercenter.registry.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public String generateToken(final String name, final boolean active) {
        // As future improvement, a JWT could be generated using name and active.
        return UUID.randomUUID().toString();
    }

    public String encodePassword(final String password) {
        return PASSWORD_ENCODER.encode(password);
    }
}
