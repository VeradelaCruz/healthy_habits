package com.example.tracker_service.dtos;

import com.example.tracker_service.models.Tracker;
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
public class TrackerWithUserAndHabit {

    @Schema(description = "Información del tracker")
    private Tracker tracker;

    @Schema(description = "Datos del usuario asociado al tracker")
    private UserDTO userDTO;

    @Schema(description = "Datos del hábito asociado al tracker")
    private HabitDTO habitDTO;
}
