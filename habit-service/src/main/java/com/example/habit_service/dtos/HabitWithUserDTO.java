package com.example.habit_service.dtos;

import com.example.habit_service.models.Habit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
@AllArgsConstructor
public class HabitWithUserDTO {

        private Habit habit;
        private UserDTO userDTO;

}
