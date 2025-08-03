package com.example.habit_service.models;

import com.example.habit_service.enums.Frequency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habit")
@Schema(description = "Entidad que representa un hábito del usuario")
public class Habit {

    @Id
    @Schema(description = "ID único generado automáticamente para el hábito", example = "688e15cd16051798a73fe04e")
    String id;

    @NotBlank(message = "A name must be provided.")
    @Schema(description = "Nombre del hábito", example = "Beber más agua", required = true)
    String name;

    @NotBlank(message = "Please write a description.")
    @Size(max = 200)
    @Schema(description = "Descripción breve del hábito", example = "Beber al menos 8 vasos de agua al día", maxLength = 200, required = true)
    String description;

    @NotNull
    @CreatedDate
    @Schema(description = "Fecha en la que se creó el hábito", example = "2025-08-03", required = true)
    LocalDate createdAt;

    @NotNull
    @Schema(description = "ID del usuario propietario del hábito", example = "688e15cd16051798a73fe04e", required = true)
    String userId;

    @NotBlank(message = "This field must be completed.")
    @Schema(description = "Categoría a la que pertenece el hábito", example = "Salud", required = true)
    String category;

    @NotNull(message = "Please specify a frequency.")
    @Schema(description = "Frecuencia con la que se debe cumplir el hábito", example = "DAILY", required = true)
    Frequency frequency;
}
