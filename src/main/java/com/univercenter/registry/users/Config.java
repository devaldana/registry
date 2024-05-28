package com.univercenter.registry.users;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services.registry")
public record Config(String passwordRegex) {}
