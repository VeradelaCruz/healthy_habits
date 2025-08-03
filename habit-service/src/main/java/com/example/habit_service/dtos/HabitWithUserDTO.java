package com.example.habit_service.dtos;

import com.example.habit_service.models.Habit;

import io.swagger.v3.oas.annotations.media.Schema;

public record HabitWithUserDTO(
        @Schema(description = "Información del habito.")
        Habit habit,

        @Schema(description = "Información del usuario asociada al habito.")
        UserDTO userDTO
) {}

