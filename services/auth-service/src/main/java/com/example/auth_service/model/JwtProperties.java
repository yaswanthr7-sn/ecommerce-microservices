package com.example.auth_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "jwt.keystore")
public record JwtProperties(
        Resource path,
        String password,
        String alias
) {
}
