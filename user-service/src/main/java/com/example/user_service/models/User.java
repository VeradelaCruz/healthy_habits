package com.example.user_service.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document
@Schema(description = "Representa un usuario del sistema")
public class User {

    @Id
    @Schema(description = "ID único generado automáticamente", example = "64d5a8f21a2f3b001f0c1234")
    private String id;

    @NotBlank(message = "A name must be provided")
    @Size(max = 20, message = "Username must be at most 20 characters")
    @Indexed(unique = true)
    @Schema(description = "Nombre de usuario único", example = "veradelacruz")
    private String userName;

    @NotBlank(message = "Email must be provided")
    @Email(message = "Email must be valid")
    @Indexed(unique = true)
    @Schema(description = "Correo electrónico único válido", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password must be provided")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Contraseña del usuario (mínimo 6 caracteres)", example = "pass1234")
    private String password;

    @CreatedDate
    @Schema(description = "Fecha de creación del usuario", example = "2025-08-03")
    private LocalDate createdAt;

    @Schema(description = "Roles asignados al usuario", example = "USER,ADMIN")
    private String roles;
}
