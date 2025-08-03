package com.example.habit_service.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;


public record UserDTO(
        @Schema(description = "Unique identifier of the user", example = "688e15cd16051798a73fe04e")
        String id,

        @Schema(description = "Username of the user", example = "user123")
        String userName,

        @Schema(description = "User email address", example = "user123@example.com")
        String email,

        @Schema(description = "User password (hashed)", example = "hashedpassword123")
        String password,

        @Schema(description = "Date when the user was created", example = "2025-08-02")
        LocalDate createdAt,

        @Schema(description = "Roles assigned to the user", example = "ADMIN")
        String roles
) {}

