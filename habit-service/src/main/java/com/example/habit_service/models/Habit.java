package com.example.habit_service.models;

import com.example.habit_service.enums.Frequency;
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
public class Habit {
    @Id
    String id;
    @NotBlank(message = "A name must be provided.")
    String name;
    @NotBlank(message = "Please write a description.")
    @Size(max=200)
    String description;
    @NotNull
    @CreatedDate
    LocalDate createdAt;
    @NotNull
    String	userId;
    @NotBlank(message = "This field must be completed.")
    String   category;
    @NotNull(message = "Please specify a frequency.")
    Frequency frequency;

}
