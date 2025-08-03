package com.example.tracker_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitDTO {

    @Schema(description = "Nombre del hábito", example = "Correr", required = true)
    String name;

    @Schema(description = "Categoría del hábito", example = "Ejercicio", required = true)
    String category;

    @Schema(description = "Frecuencia del hábito", example = "USUALLY", required = true)
    String frequency;
}
