package com.example.auth_service.model;

public record UserResponse(
        Long id,
        String username,
        String role
) {
}
