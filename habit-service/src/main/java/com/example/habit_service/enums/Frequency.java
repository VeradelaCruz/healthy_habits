package com.example.habit_service.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Frecuencia con la que se realiza un hábito")
public enum Frequency {
    @Schema(description = "Usualmente")
    USUALLY,

    @Schema(description = "A veces")
    SOMETIMES,

    @Schema(description = "De vez en cuando")
    ONCE_IN_A_WHILE,

    @Schema(description = "Nunca")
    NEVER
}

