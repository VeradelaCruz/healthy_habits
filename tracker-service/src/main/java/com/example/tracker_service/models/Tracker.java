package com.example.tracker_service.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@AllArgsConstructor
@Document
public class Tracker {

    @Id
    @Schema(description = "ID único del tracker", example = "abc123")
    String id;

    @NotBlank(message = "Please assign a user.")
    @Schema(description = "ID del usuario que realiza el seguimiento", example = "user456")
    String userId;

    @NotBlank(message = "Please assign a habit.")
    @Schema(description = "ID del hábito que se está siguiendo", example = "habit789")
    String habitId;

    @NotNull
    @CreatedDate
    @Schema(description = "Fecha en que se registró el seguimiento", example = "2025-08-03")
    LocalDate date;

    @NotNull(message = "Completion status is required.")
    @Schema(description = "Indica si el hábito fue completado", example = "true")
    Boolean completed;

    @Size(max = 300)
    @Schema(description = "Notas opcionales sobre el seguimiento", example = "Hice 30 minutos de cardio")
    String notes;
}

